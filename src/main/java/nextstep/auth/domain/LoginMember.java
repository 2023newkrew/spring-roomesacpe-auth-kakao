package nextstep.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nextstep.member.Member;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class LoginMember {

    private final Long id;

    private final String username;

    private final String name;

    private final Role role;

    public static LoginMember from(Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }

    public boolean isAdmin() {
        return Role.ADMIN.equals(role);
    }
}
