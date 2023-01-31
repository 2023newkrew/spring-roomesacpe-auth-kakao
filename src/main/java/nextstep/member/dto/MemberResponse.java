package nextstep.member.dto;

import nextstep.member.Member;

public class MemberResponse {

    private final String name;
    private final String username;
    private final String phone;

    private MemberResponse(String name, String username, String phone) {
        this.name = name;
        this.username = username;
        this.phone = phone;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getName(), member.getUsername(), member.getPhone());
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }
}
