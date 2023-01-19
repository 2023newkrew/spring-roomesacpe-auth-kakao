package nextstep.framework.auth.principal;

import nextstep.framework.auth.util.AuthorizationExtractor;
import nextstep.framework.auth.jwt.JwtTokenProvider;
import nextstep.framework.exception.AuthErrorCode;
import nextstep.framework.exception.BusinessException;
import nextstep.publics.member.MemberDao;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = AuthorizationExtractor.extractTokenFromRequest();
        if (!jwtTokenProvider.validateToken(token)) {
            throw new BusinessException(AuthErrorCode.TOKEN_REQUIRED);
        }
        Long id = Long.parseLong(jwtTokenProvider.getPrincipal(token));

        return memberDao.findById(id);
    }
}