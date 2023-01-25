package nextstep.auth;

import nextstep.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationValue = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationValue == null || !authorizationValue.startsWith(BEARER_TOKEN_PREFIX)) {
            throw new UnauthorizedException();
        }

        String token = authorizationValue.substring(BEARER_TOKEN_PREFIX.length());

        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException();
        }

        return Long.valueOf(jwtTokenProvider.getPrincipal(token));
    }
}
