package nextstep.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Authority authority;

    static public Member of(String username, String password, String name, String phone) {
        return new Member(null, username, password, name, phone, Authority.USER);
    }

    public boolean isAdmin() {
        return Objects.equals(authority, Authority.ADMIN);
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
