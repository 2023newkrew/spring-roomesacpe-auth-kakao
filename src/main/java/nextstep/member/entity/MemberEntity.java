package nextstep.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
}
