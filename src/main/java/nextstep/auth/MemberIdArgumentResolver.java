package nextstep.auth;

import nextstep.member.Role;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isMemberId = parameter
                .getParameterAnnotation(LoginMember.class) != null;

        boolean isLoginMember = nextstep.member.LoginMember.class.equals(parameter.getParameterType());

        return isMemberId && isLoginMember;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        Long id = Long.parseLong((String) webRequest.getAttribute("id", RequestAttributes.SCOPE_REQUEST));
        Role role = (Role) webRequest.getAttribute("role", RequestAttributes.SCOPE_REQUEST);
        return new nextstep.member.LoginMember(id, role);
    }
}