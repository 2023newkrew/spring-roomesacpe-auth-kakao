package nextstep.member;

public class Member {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone);
    }

    public Member(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
