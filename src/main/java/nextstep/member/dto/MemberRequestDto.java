package nextstep.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.member.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class MemberRequestDto {

    private String username;
    private String password;
    private String name;
    private String phone;
    private String role;

    public Member toEntity() {
        return new Member(username, password, name, phone, role);
    }
}
