package nextstep.config;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.AuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        boolean isValidToken = jwtTokenProvider.validateToken(token);

        if(!isValidToken) {
            throw new AuthorizationException();
        }

        return jwtTokenProvider.getPrincipal(token);
    }
}