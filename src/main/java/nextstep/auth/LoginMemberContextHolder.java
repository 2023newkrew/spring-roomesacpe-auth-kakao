package nextstep.auth;

import nextstep.auth.domain.LoginMember;
import org.springframework.stereotype.Component;

@Component
public class LoginMemberContextHolder {

    private final ThreadLocal<LoginMember> contextHolder = new ThreadLocal<>();

    public void clearContext() {
        contextHolder.remove();
    }

    public LoginMember getContext() {
        return contextHolder.get();
    }

    public void setContext(LoginMember loginMember) {
        contextHolder.set(loginMember);
    }
}
