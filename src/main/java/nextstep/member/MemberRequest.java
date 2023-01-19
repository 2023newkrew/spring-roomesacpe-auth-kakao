package nextstep.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.auth.Role;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .role(Role.USER)
                .name(name)
                .phone(phone)
                .build();
    }
}
