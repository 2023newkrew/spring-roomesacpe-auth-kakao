package nextstep.config;

import static nextstep.auth.AuthorizationExtractor.getTokenFromHeader;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authHeader = webRequest.getHeader(AUTHORIZATION);
        String token = getTokenFromHeader(authHeader);

        return jwtTokenProvider.getAuthMember(token);
    }
}
