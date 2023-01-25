package nextstep.auth.principal;

import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static nextstep.auth.authorization.LoginInterceptor.authorization;
import static nextstep.auth.authorization.LoginInterceptor.bearer;
import static nextstep.config.Messages.*;


@Component
public class MemberAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public MemberAuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberAuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String accessToken = Objects.requireNonNull(webRequest.getHeader(authorization),
                LOGIN_NEEDS.getMessage()).substring(bearer.length());
        String principal = jwtTokenProvider.getPrincipal(accessToken);
        return memberService.findByUsername(principal).get(0);
    }
}
