package nextstep.member;

public class Member {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;
    private final Role role;

    public Member(Long id, String username, String password, String name, String phone, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public Member(Long id, String username, String password, String name, String phone, String role) {
        this(id, username, password, name, phone, Role.of(role));
    }

    public Member(String username, String password, String name, String phone, Role role) {
        this(null, username, password, name, phone, role);
    }

    public Member(Long id, String username, String password, String name, String phone) {
        this(id, username, password, name, phone, Role.MEMBER);
    }

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone, Role.MEMBER);
    }

    public boolean isAdmin() {
        return role.isAdmin();
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

    public String getRole() {
        return role.getRole();
    }
}
