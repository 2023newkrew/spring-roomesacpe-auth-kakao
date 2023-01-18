package nextstep.auth;

import nextstep.member.Member;

public class LoggedInMember {
    private final Long id;
    private final String name;

    private LoggedInMember(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static LoggedInMember from(Member member) {
        return new LoggedInMember(member.getId(), member.getName());
    }
}
