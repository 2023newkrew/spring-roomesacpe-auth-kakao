package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.AuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "authorization";

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader(AUTHORIZATION);
        Member member = memberDao.findByUsername(jwtTokenProvider.getPrincipal(token.substring(BEARER.length())));

        if (isAuthenticatedUser(request, member)) {
            throw new AuthorizationException();
        }

        return member;
    }

    private boolean isAuthenticatedUser(HttpServletRequest request, Member member) {
        return request.getHeader(AUTHORIZATION).isBlank() || ObjectUtils.isEmpty(member);
    }
}
