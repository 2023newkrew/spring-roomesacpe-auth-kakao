package nextstep.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ACCESS_TOKEN_NAME = "authorization";

    private final JwtTokenProvider provider;

    public MemberIdArgumentResolver(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isMemberId = parameter
                .getParameterAnnotation(MemberId.class) != null;

        boolean isLong = Long.class.equals(parameter.getParameterType());

        return isMemberId && isLong;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        var bearerToken = webRequest.getHeader(ACCESS_TOKEN_NAME);

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new AuthenticationException();
        }

        var accessToken = bearerToken.substring(7);

        if (!provider.validateToken(accessToken)) {
            throw new AuthenticationException();
        }

        return Long.parseLong(provider.getPrincipal(accessToken));
    }
}