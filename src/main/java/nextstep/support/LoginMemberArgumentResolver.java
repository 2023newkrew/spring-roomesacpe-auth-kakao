package nextstep.support;

import nextstep.auth.AuthorizationTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberDao;
import nextstep.support.excpetion.InvalidAuthorizationTokenException;
import nextstep.support.excpetion.NotExistMemberException;
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

    public LoginMemberArgumentResolver(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = AuthorizationTokenExtractor.extract(
                webRequest.getHeader(AuthorizationTokenExtractor.AUTHORIZATION))
                .orElseThrow(InvalidAuthorizationTokenException::new);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException();
        }

        return memberDao.findById(jwtTokenProvider.getMemberId(token))
                .orElseThrow(NotExistMemberException::new);
    }
}
