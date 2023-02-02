package nextstep.config.auth;

import nextstep.auth.JwtTokenProvider;
import nextstep.support.UnAuthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(AUTHORIZATION);
        if (!(header.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
            throw new UnAuthorizedException();
        }

        String token = parseTokenFromHeader(header);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnAuthorizedException();
        }

        return Long.parseLong(jwtTokenProvider.getPrincipal(token));
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
