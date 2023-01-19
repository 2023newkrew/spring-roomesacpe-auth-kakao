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
    private Authority authority;

    static public Member ofUser(String username, String password, String name, String phone) {
        return new Member(null, username, password, name, phone, Authority.USER);
    }

    static public Member ofAdmin(String username, String password, String name, String phone) {
        return new Member(null, username, password, name, phone, Authority.ADMIN);
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
