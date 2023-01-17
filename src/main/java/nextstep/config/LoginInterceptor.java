package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.AuthorizationException;
import nextstep.support.DuplicateEntityException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");

        // GET 메서드가 아니고 (GET은 예약 목록 조회에만 사용되고 있기 때문에 로그인 하지 않아도 가능해야 함), token이 없을 때
        if(!HttpMethod.GET.matches(request.getMethod()) && accessToken == null) {
            throw new AuthorizationException();
        }

        // token 만료 되었는지 확인
        if(!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthorizationException();
        }

        return super.preHandle(request, response, handler);
    }
}