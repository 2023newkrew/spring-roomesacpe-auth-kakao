package nextstep.login;

import nextstep.member.Member;

public class LoginMember {

    private Long id;
    private String username;
    private String name;
    private String phone;

    public LoginMember(Long id, String username, String name, String phone) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.phone = phone;
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

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getUsername(), member.getName(), member.getPhone());
    }
}
