package nextstep.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.member.entity.MemberEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
}
