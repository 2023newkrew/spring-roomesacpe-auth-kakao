package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.ErrorCode;
import nextstep.exception.RoomEscapeException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
    private final String BEARER = "Bearer ";

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String accessToken = request.getHeader("authorization").substring(BEARER.length());
        Member member = getMemberOrThrowException(
                jwtTokenProvider.getPrincipal(accessToken)
        );

        if(!member.getRole().equals("ROLE_ADMIN")){
            throw new RoomEscapeException(ErrorCode.ADMIN_ONLY);
        }

        return true;
    }

    private Member getMemberOrThrowException(String username){
        Member member =  memberDao.findByUsername(username);

        if(member == null){
            throw new RoomEscapeException(ErrorCode.NO_SUCH_MEMBER);
        }

        return member;
    }
}
