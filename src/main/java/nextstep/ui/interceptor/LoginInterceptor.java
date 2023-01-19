package nextstep.ui.interceptor;

import nextstep.auth.JwtTokenProvider;
import nextstep.login.LoginMember;
import nextstep.login.LoginService;
import nextstep.support.TokenExpirationException;
import nextstep.support.UnauthorizedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final LoginService loginService;

    public LoginInterceptor(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        // 인증 헤더 확인
        String header = request.getHeader("authorization");
        if (header == null) throw new UnauthorizedException();

        // 토큰 만료 확인
        String token = header.substring("Bearer ".length());
        if (!jwtTokenProvider.validateToken(token)) throw new TokenExpirationException();

        // 해당 회원 확인
        String username = jwtTokenProvider.getPrincipal(token);
        LoginMember loginMember = loginService.findByUsername(username);

        request.setAttribute("username", loginMember.getUsername());
        request.setAttribute("role", loginMember.getRole());

        return super.preHandle(request, response, handler);
    }



}
