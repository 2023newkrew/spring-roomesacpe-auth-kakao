package nextstep.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ACCESS_TOKEN_NAME = "authorization";

    private final JwtTokenProvider provider;

    public MemberIdArgumentResolver(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        MemberId memberId = parameter.getParameterAnnotation(MemberId.class);
        boolean isLong = Long.class.equals(parameter.getParameterType());

        return Objects.nonNull(memberId) && isLong;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        var bearerToken = webRequest.getHeader(ACCESS_TOKEN_NAME);
        var accessToken = provider.getValidToken(bearerToken);
        String principal = provider.getPrincipal(accessToken);

        return Long.parseLong(principal);
    }
}