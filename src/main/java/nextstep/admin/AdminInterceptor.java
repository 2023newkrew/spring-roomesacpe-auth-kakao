package nextstep.admin;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = jwtTokenExtractor.extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        boolean result = jwtTokenProvider.getRole(token).map(s -> s.equals(Role.ADMIN.name())).orElse(false);
        if (!result) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return result;
    }
}
