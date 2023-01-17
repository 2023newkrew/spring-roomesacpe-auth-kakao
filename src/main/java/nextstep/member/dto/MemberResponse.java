package nextstep.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private String username;
    private String password;
    private String name;
    private String phone;
}
