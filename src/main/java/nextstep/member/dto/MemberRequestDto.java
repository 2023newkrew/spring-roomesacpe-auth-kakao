package nextstep.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.member.Member;

@Getter
@AllArgsConstructor
public class MemberRequestDto {
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member toEntity() {
        return new Member(username, password, name, phone);
    }
}
