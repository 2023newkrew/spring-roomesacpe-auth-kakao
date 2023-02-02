package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Boolean admin;

    public Member(Long id, String username, String password, String name, String phone) {
        this(id, username, password, name, phone, null);
    }

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone, null);
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
