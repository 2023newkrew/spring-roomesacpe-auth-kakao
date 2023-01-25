package nextstep.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.member.Member;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginMember {

    private final Long id;

    private final String username;

    private final String name;

    private final List<Role> roles = new ArrayList<>(List.of(Role.USER));

    public static LoginMember from(Member member) {
        return new LoginMember(
                member.getId(), member.getUsername(), member.getName()
        );
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
