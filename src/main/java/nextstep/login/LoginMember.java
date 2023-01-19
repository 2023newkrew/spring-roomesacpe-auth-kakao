package nextstep.login;

import nextstep.member.Member;
import nextstep.member.MemberRole;

public class LoginMember {

    private Long id;
    private String username;
    private String name;
    private String phone;
    private MemberRole role;

    public LoginMember(Long id, String username, String name, String phone, MemberRole role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public MemberRole getRole() {
        return role;
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getUsername(), member.getName(), member.getPhone(), member.getRole());
    }
}
