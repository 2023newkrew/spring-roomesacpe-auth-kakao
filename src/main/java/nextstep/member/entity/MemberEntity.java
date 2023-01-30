package nextstep.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberEntity {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String role;
}
