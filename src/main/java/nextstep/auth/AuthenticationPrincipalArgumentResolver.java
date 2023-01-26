package nextstep.auth;

import nextstep.support.UnauthorizedException;
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
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String headerValue = AuthorizationExtractor.extract(request);
        if (headerValue == null) {
            throw new UnauthorizedException("토큰이 존재하지 않습니다.");
        }
        try {
            String payload = new JwtTokenProvider().getPrincipal(headerValue);
            if (payload == null) {
                return null;
            }
            return Long.parseLong(payload);
        }
        catch (RuntimeException ex){
            throw new UnauthorizedException("잘못된 토큰입니다.");
        }
    }
}
