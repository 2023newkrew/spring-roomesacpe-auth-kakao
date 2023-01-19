package nextstep.auth;

import nextstep.member.Member;

public class LoginMember {
    private final Long id;
    private final String username;
    private final Role role;

    private LoginMember(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static LoginMember of(Member member, Role role) {
        return new LoginMember(member.getId(), member.getUsername(), role);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}