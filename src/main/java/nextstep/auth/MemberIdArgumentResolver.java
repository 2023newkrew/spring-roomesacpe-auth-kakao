package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

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
            WebDataBinderFactory binderFactory) {

        String accessToken = TokenExtractor.extract(webRequest);

        if (!provider.validateToken(accessToken)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        return Long.parseLong(provider.getPrincipal(accessToken));
    }
}