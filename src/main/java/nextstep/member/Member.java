package nextstep.member;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = Role.USER;
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
