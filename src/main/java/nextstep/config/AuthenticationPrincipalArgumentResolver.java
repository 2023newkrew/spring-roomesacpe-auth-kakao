package nextstep.config;

import nextstep.auth.AuthService;
import nextstep.auth.AuthorizationExtractor;
import nextstep.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;
    private final MemberService memberService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService, MemberService memberService){
        this.authService = authService;
        this.memberService =  memberService;
    }
    // resolveArgument 메서드가 동작하는 조건을 정의하는 메서드
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터 중 @AuthenticationPrincipal이 붙은 경우 동작하게 설정
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    // supportsParameter가 true인 경우 동작하는 메서드
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)webRequest.getNativeRequest();
        String token = AuthorizationExtractor.extract(httpServletRequest);
        Long id = authService.validateToken(token);

        return memberService.findById(id);
    }
}
