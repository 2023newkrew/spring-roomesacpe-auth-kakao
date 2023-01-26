package nextstep.member;

import lombok.Builder;
import lombok.Getter;
import nextstep.auth.Role;

@Builder
@Getter
public class Member {

    private Long id;
    private String username;
    private String password;
    private Role role;
    private String name;
    private String phone;

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }
}
