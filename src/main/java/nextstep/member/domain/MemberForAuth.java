package nextstep.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.auth.util.PasswordUtil;

@Getter
@AllArgsConstructor
public class MemberForAuth {

    protected final String username;
    protected String password;

    public final boolean matchPassword(String rawPassword) {

        return PasswordUtil.matches(rawPassword, this.password);
    }

    public final void encryptPassword() {
        this.password = PasswordUtil.encrypt(this.password);
    }
}
