package nextstep.member;

import java.util.Objects;

public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member() {
    }

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone, Role.USER);
    }

    public Member(String username, String password, String name, String phone, Role role) {
        this(null, username, password, name, phone, role);
    }

    public Member(Long id, String username, String password, String name, String phone, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(username, member.username) && Objects.equals(password, member.password) && Objects.equals(name, member.name) && Objects.equals(phone, member.phone) && role == member.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, phone, role);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}
