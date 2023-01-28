package nextstep.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.member.Member;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginMember {

    private final Long id;

    private final String username;

    private final String name;

    private final Role role;

    public static LoginMember from(Member member) {
        return new LoginMember(
                member.getId(), member.getUsername(), member.getName(), member.getRole()
        );
    }

    public boolean isAdmin() {
        return Role.ADMIN.equals(role);
    }
}
