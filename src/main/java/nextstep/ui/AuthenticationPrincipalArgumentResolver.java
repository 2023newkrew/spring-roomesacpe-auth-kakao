package nextstep.ui;

import nextstep.auth.JwtTokenProvider;
import nextstep.login.LoginMember;
import nextstep.login.LoginService;
import nextstep.member.Member;
import nextstep.support.TokenExpirationException;
import nextstep.support.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final LoginService loginService;

    public AuthenticationPrincipalArgumentResolver(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String header = httpServletRequest.getHeader("authorization");
        if (header == null) throw new UnauthorizedException();

        String token = header.substring("Bearer ".length());
        if (!jwtTokenProvider.validateToken(token)) throw new TokenExpirationException();

        String username = jwtTokenProvider.getPrincipal(token);
        Member member = loginService.findByUsername(username);
        return LoginMember.of(member);
    }

}
