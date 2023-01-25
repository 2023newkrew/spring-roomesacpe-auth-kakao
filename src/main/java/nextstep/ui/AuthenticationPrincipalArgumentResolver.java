package nextstep.ui;

import nextstep.auth.AuthorizationExtractor;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    public AuthenticationPrincipalArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request
                = (HttpServletRequest) webRequest.getNativeRequest();

        String token = AuthorizationExtractor.extract(request);

        if (token == null) {
            throw new AuthenticationException();
        }

        Member member = memberService.findByToken(token);

        return member;
    }
}
