package nextstep.auth;

import nextstep.support.AuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        System.out.println("지나감1");
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String headerValue = AuthorizationExtractor.extract(request);
        System.out.println("지나감2");
        if (headerValue == null) {
            throw new AuthorizationException();
        }
        System.out.println("통과?");
        String payload = new JwtTokenProvider().getPrincipal(headerValue);
        if (payload == null) {
            return null;
        }
        return Long.parseLong(payload);
    }
}
