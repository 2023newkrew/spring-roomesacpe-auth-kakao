package nextstep.auth;

import nextstep.member.MemberDao;
import nextstep.support.NotExistEntityException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
        System.out.println(parameter.hasParameterAnnotation(AuthenticationPrincipal.class));
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = AuthorizationExtractor.extract(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        if (!jwtTokenProvider.validateToken(token)) {
            throw new NotExistEntityException();
        }
        System.out.println(jwtTokenProvider.getPrincipal(token) + "여기에");
        Long id = Long.parseLong(jwtTokenProvider.getPrincipal(token));

        return memberDao.findById(id);
    }
}