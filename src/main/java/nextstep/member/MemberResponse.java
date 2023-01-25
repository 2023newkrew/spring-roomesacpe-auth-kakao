package nextstep.member;

import nextstep.login.LoginMember;

public class MemberResponse {
    private String username;
    private String name;
    private String phone;

    public MemberResponse(String username, String name, String phone) {
        this.username = username;
        this.name = name;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public static MemberResponse of(LoginMember loginMember) {
        return new MemberResponse(loginMember.getUsername(), loginMember.getName(), loginMember.getPhone());
    }
}
