package nextstep.config;

import nextstep.auth.AuthService;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.LoginMember;
import nextstep.member.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * AuthenticationPrincipalArgumentResolver is the class
 * what would be done when {@code @AuthenticationPrincipal} is attached.
 * <br>
 * If header has token, validate and returns LoginMember instance, which contains username of member.
 */
@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;
    private final MemberService memberService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    // resolveArgument 메서드가 동작하는 조건을 정의하는 메서드
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터 중 @AuthenticationPrincipal이 붙은 경우 동작하게 설정
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    // supportsParameter가 true인 경우 동작하는 메서드

    /**
     * This method contains what to do if @AuthenticationPrincipal is attached at controller parameter.
     * Automatically checks if header has token, and returns username of token.
     *
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return username of token if successfully validated.
     */
    // 원래 토큰의 id(member_id)를 반환하는 것도 고려하였으나,
    // 예약 정보에 member_id를 가지지 않고 username을 가지기 때문에
    // 예약 삭제 시 username끼리 비교하여 authenticate를 할 수 있도록 하기 위해
    // username을 반환하도록 하였다
    // (schema에서 username를 unique하다고 정의하였음 - 대체키로 사용될 수 있음)
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = AuthorizationExtractor.extract(httpServletRequest);
        Long id = authService.getMemberIdFromToken(token);

        return new LoginMember(memberService.findById(id).getId());
    }
}
