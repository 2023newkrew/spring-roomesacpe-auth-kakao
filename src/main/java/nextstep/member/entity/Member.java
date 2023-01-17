package nextstep.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
