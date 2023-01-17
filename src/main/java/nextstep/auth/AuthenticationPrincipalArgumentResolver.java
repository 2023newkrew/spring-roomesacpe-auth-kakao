package nextstep.auth;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터 중 @AuthenticationPrincipal이 붙은 경우 동작하게 설정
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("authorization");
        String token = extractToken(authorization);

        validateToken(token);

        return getPrincipalFromToken(token);
    }

    private String extractToken(String authorization) {
        return Optional.ofNullable(authorization)
                .map(v -> v.split("Bearer ")[1])
                .orElseThrow();
    }

    private void validateToken(String token) {
        JwtTokenProvider.validateToken(token);
    }

    private String getPrincipalFromToken(String token) {
        return JwtTokenProvider.getPrincipal(token);
    }
}