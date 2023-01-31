package nextstep.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nextstep.global.exception.AuthFailException;
import nextstep.global.exception.NotExistEntityException;
import nextstep.global.util.AuthorizationHeaderExtractor;
import nextstep.global.util.AuthorizationHeaderExtractor.TokenType;
import nextstep.global.util.JwtTokenProvider;
import nextstep.member.dao.MemberDao;
import nextstep.member.entity.MemberEntity;
import nextstep.member.enums.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationHeaderExtractor.extract(request, TokenType.BEARER)
            .orElse("");

        try {
            jwtTokenProvider.validateToken(token);
            Long memberId = Long.parseLong(jwtTokenProvider.getPrincipal(token));
            MemberEntity member = memberDao.findById(memberId)
                    .orElseThrow(NotExistEntityException::new);

            if (!member.getRole().equals(Role.ADMIN)) {
                throw new AuthFailException();
            }
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
