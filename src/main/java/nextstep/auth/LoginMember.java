package nextstep.auth;

import nextstep.member.Member;

public class LoginMember {
    private final Long id;
    private final String username;

    private LoginMember(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getUsername());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
