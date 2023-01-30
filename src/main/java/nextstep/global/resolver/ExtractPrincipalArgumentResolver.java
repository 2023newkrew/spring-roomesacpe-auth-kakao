package nextstep.global.resolver;

import nextstep.auth.domain.AccessToken;
import nextstep.global.annotation.ExtractPrincipal;
import nextstep.global.exception.UnauthenticatedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class ExtractPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(ExtractPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("authorization");
        String token = extractToken(authorization);

        AccessToken accessToken = AccessToken.from(token);
        if (!accessToken.isValid()) {
            throw new UnauthenticatedException();
        }

        return accessToken.getSub();
    }

    private String extractToken(String authorization) {
        return Optional.ofNullable(authorization)
                .map(v -> v.split("Bearer ")[1])
                .orElseThrow();
    }
}