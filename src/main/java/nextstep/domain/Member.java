package nextstep.domain;

import nextstep.dto.request.MemberRequest;

public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member() {
    }

    public Member(Long id, String username, String password, String name, String phone, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public Member(MemberRequest memberRequest) {
        this.username = memberRequest.getUsername();
        this.password = memberRequest.getPassword();
        this.name = memberRequest.getName();
        this.phone = memberRequest.getPhone();
        this.role = Role.USER;
    }

    public Long getId() {
        return id;
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

    public Role getRole() {
        return role;
    }

    public boolean isInvalidPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }
}
