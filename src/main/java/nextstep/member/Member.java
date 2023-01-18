package nextstep.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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
