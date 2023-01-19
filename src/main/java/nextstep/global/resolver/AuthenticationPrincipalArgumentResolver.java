package nextstep.global.resolver;

import nextstep.global.annotation.AuthenticationPrincipal;
import nextstep.global.util.JwtTokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = extractToken(webRequest.getHeader("authorization"));
        validateToken(token);

        return getPrincipleFromToken(token);
    }

    private String extractToken(String authorization) {
        return Optional.ofNullable(authorization)
                .map(auth -> auth.split("Bearer ")[1])
                .orElseThrow();
    }

    private void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }

    private String getPrincipleFromToken(String token) {
        return jwtTokenProvider.getPrincipal(token);
    }
}
