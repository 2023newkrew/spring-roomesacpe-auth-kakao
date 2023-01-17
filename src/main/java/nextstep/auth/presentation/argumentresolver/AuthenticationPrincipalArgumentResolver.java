package nextstep.auth.presentation.argumentresolver;

import nextstep.auth.utils.JwtTokenProvider;
import nextstep.dto.request.LoginMember;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ACCESS_TOKEN = "accessToken";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthenticationPrincipalAnnotation = parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
        boolean hasLoginMemberType = LoginMember.class.isAssignableFrom(parameter.getParameterType());

        return hasAuthenticationPrincipalAnnotation && hasLoginMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = String.valueOf(request.getAttribute(ACCESS_TOKEN));

        String principal = jwtTokenProvider.getPrincipal(token);
        return new LoginMember(Long.valueOf(principal));
    }

}
