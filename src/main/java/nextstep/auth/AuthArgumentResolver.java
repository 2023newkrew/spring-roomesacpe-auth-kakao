package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = (String) nativeRequest.getAttribute("accessToken");
        return authService.getMemberFromToken(token);
    }
}
