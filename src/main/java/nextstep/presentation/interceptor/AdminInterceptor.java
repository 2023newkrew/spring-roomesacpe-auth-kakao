package nextstep.presentation.interceptor;

import lombok.RequiredArgsConstructor;
import nextstep.domain.member.MemberRole;
import nextstep.error.ApplicationException;
import nextstep.utils.JwtTokenProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.error.ErrorType.UNAUTHORIZED_ERROR;

@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN = "accessToken";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = String.valueOf(request.getAttribute(ACCESS_TOKEN));
        MemberRole role = MemberRole.valueOf(String.valueOf(jwtTokenProvider.getClaim(token, "role")));

        if (!role.equals(MemberRole.ADMIN)) {
            throw new ApplicationException(UNAUTHORIZED_ERROR);
        }

        return true;
    }
}
