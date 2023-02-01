package nextstep.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.member.enums.Role;

@Getter
@AllArgsConstructor
public class MemberEntity {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public MemberEntity(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = Role.USER;
    }
}
