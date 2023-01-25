package nextstep.support;

import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;
    private final AuthorizationTokenExtractor authorizationTokenExtractor;

    public LoginMemberArgumentResolver(JwtTokenProvider jwtTokenProvider, MemberDao memberDao, AuthorizationTokenExtractor authorizationTokenExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
        this.authorizationTokenExtractor = authorizationTokenExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class) &&
                parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String token = authorizationTokenExtractor.extract(webRequest);

        String username = jwtTokenProvider.getPrincipal(token);

        return memberDao.findByUsername(username)
                .orElseThrow(() -> new NotExistEntityException(ErrorCode.USER_NOT_FOUND));
    }
}
