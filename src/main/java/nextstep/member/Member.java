package nextstep.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member(Long id, String username, String password, String name, String phone, String role) {
        this(id, username, password, name, phone, Role.from(role));
    }

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone, Role.MEMBER);
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
