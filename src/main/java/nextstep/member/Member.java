package nextstep.member;

public class Member {
    private Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;
    private final Role role;

    public Member(Long id, String username, String password, String name, String phone, Role role) {
        this(username, password, name, phone, role);
        this.id = id;
    }

    public Member(String username, String password, String name, String phone, Role role) {
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

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}
