package nextstep.auth.presentation.argumentresolver;

import nextstep.auth.utils.JwtTokenProvider;
import nextstep.dto.request.LoginMember;
import nextstep.error.ApplicationException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static nextstep.error.ErrorType.UNAUTHORIZED_ERROR;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

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
        String token = extractToken(request);

        String principal = jwtTokenProvider.getPrincipal(token);
        return new LoginMember(Long.valueOf(principal));
    }

    private String extractToken(HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            return authorization.split(" ")[1];
        } catch (Exception e) {
            throw new ApplicationException(UNAUTHORIZED_ERROR);
        }
    }

}
