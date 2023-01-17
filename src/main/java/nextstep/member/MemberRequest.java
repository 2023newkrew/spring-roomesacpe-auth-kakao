package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member toEntity() {
        return new Member(username, password, name, phone);
    }
}
