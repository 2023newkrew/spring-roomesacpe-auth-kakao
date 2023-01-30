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

import static nextstep.support.ErrorMessage.LOGIN_FAIL;
import static nextstep.support.ErrorMessage.TOKEN_EXPIRATION;

@Component
@RequiredArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");

        // GET 메서드가 아닐때 (GET은 예약 목록 조회에만 사용되고 있기 때문에 로그인 하지 않아도 가능해야 함)
        if(!HttpMethod.GET.matches(request.getMethod())) {
<<<<<<< HEAD
            // 로그인 검증 로직
            checkLogin(accessToken);
=======
            // 검증 로직들
            checkNullToken(accessToken);
            checkTokenExpiration(accessToken);
>>>>>>> kimtaehyun98
        }

        return super.preHandle(request, response, handler);
    }

<<<<<<< HEAD
    private void checkLogin(String accessToken){
        checkNullToken(accessToken);
        checkTokenExpiration(accessToken);
    }

=======
>>>>>>> kimtaehyun98
    // token 값이 비어있는지 확인
    private void checkNullToken(String accessToken){
        if(accessToken == null) {
            throw new AuthorizationException(LOGIN_FAIL);
        }
    }

    // token 만료 되었는지 확인
    private void checkTokenExpiration(String accessToken){
        String token = accessToken.split(" ")[1];
        if(!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException(TOKEN_EXPIRATION);
        }
    }
}