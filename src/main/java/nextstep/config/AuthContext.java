package nextstep.config;

import nextstep.auth.AuthMemberDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    ThreadLocal<AuthMemberDTO> authMemberStorage = new ThreadLocal<>();

    public void setAuthMember(AuthMemberDTO authMember) {
        authMemberStorage.set(authMember);
    }

    public AuthMemberDTO getAuthMember() {
        return authMemberStorage.get();
    }

    public void clear() {
        authMemberStorage.remove();
    }
}
