package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
    private MemberRole role;

    public Member(String username, String password, String name, String phone, String roleName) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = MemberRole.findBy(roleName);
    }

    public boolean isMyPassword(String password) {
        return this.password.equals(password);
    }
}
