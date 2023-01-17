package nextstep.member;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    public MemberRequest(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public Member toEntity() {
        return new Member(username, password, name, phone);
    }
}
