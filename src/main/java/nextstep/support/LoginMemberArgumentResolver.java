package nextstep.support;

import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
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

    public LoginMemberArgumentResolver(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String token = AuthorizationTokenExtractor.extract(
                webRequest.getHeader(AuthorizationTokenExtractor.AUTHORIZATION))
                .orElseThrow(() -> new InvalidAuthorizationTokenException(ErrorCode.INVALID_TOKEN));

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = jwtTokenProvider.getPrincipal(token);
        return memberDao.findByUsername(username)
                .orElseThrow(() -> new NotExistEntityException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
