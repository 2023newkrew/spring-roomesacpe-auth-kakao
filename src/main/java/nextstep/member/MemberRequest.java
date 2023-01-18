package nextstep.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .phone(phone)
                .build();
    }
}
