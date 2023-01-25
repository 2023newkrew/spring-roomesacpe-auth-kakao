package nextstep.interceptor;

import nextstep.auth.Administrator;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.BusinessException;
import nextstep.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String userRole = jwtTokenProvider.getRole(parseToken(request.getHeader("Authorization")));

        if (((HandlerMethod) handler).hasMethodAnnotation(Administrator.class) && !userRole.equals("admin")) {
            throw new BusinessException(ErrorCode.NOT_AUTHENTICATED);
        }

        return true;
    }

    private String parseToken(String authHeader) {
        return authHeader.substring(6).trim();
    }

}
