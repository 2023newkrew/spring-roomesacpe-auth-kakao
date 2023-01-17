package nextstep.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberForAuth {

    private final String username;
    private String password;

    public boolean matchPassword(String rawPassword) {
        return this.password.equals(rawPassword);
    }

    public void encryptPassword() {
        this.password = encryptPassword(this.password);
    }

    private String encryptPassword(String rawPassword) {
        return "";
    }
}
