package nextstep.member;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
