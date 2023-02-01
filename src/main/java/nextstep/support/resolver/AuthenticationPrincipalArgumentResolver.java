package nextstep.support.resolver;

import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberService;
import nextstep.support.annotation.AuthorizationPrincipal;
import nextstep.support.exception.ResolverException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization");

        if (!jwtTokenProvider.validateToken(token)) {
            throw new ResolverException("invalid token", HttpStatus.UNAUTHORIZED);
        }

        return memberService.findById(Long.parseLong(jwtTokenProvider.getPrincipal(token)));
    }
}
