package nextstep.config.auth;

import nextstep.auth.AuthService;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.UnAuthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public LoginUserArgumentResolver(JwtTokenProvider jwtTokenProvider, AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(AUTHORIZATION);
        if (!(header.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
            throw new UnAuthorizedException();
        }

        String token = parseTokenFromHeader(header);

        if(!jwtTokenProvider.validateToken(token)) {
            throw new UnAuthorizedException();
        }

        String username = jwtTokenProvider.getPrincipal(token);
        return authService.findByUsername(username);
    }

    private String parseTokenFromHeader(String header) {
        String token = header.substring(BEARER_TYPE.length()).trim();
        int commaIndex = token.indexOf(',');
        if (commaIndex > 0) {
            token = token.substring(0, commaIndex);
        }
        return token;
    }
}
