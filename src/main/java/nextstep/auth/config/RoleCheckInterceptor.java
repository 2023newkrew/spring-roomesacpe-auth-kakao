package nextstep.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Configuration
public class RoleCheckInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public RoleCheckInterceptor(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws  Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        String role = jwtTokenProvider.getRole(token);

        return Objects.equals(role, "admin");
    }
}
