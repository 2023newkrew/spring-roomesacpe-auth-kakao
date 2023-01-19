package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.auth.Authority;

@Getter
@AllArgsConstructor
public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member toEntity(Authority authority) {
        return new Member(null, username, password, name, phone, authority);
    }
}
