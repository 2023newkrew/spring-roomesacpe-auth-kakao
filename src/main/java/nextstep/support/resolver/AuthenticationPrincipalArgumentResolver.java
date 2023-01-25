package nextstep.support.resolver;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.LoginMember;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.annotation.AuthorizationPrincipal;
import nextstep.support.exception.AuthorizationExcpetion;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = jwtTokenExtractor.extractToken(webRequest.getHeader(HttpHeaders.AUTHORIZATION));
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationExcpetion(RoomEscapeExceptionCode.INVALID_TOKEN);
        }
        Member member = memberService.findByUsername(jwtTokenProvider.getPrincipal(token));
        return LoginMember.from(member);
    }
}
