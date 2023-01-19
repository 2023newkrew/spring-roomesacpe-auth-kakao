package nextstep.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone);
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
