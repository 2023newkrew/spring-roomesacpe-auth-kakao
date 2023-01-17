package nextstep.member.dto;

import nextstep.member.Member;

public class LoginMember {
    private final Long id;
    private final String username;
    private final String name;

    private LoginMember(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getUsername(), member.getName());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
