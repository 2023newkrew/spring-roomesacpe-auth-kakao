package nextstep.member.dto;

import nextstep.member.Member;
import nextstep.member.MemberRole;

public class MemberRequest {
    private final String username;
    private final String password;
    private final String name;
    private final String phone;

    public MemberRequest(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Member toEntity() {
        return new Member(username, password, name, phone, MemberRole.USER);
    }
}
