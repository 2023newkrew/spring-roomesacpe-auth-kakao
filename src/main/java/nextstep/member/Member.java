package nextstep.member;

public class Member {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;
    private final boolean isAdmin;


    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone);
    }

    public Member(Long id, String username, String password, String name, String phone) {
        this(id, username, password, name, phone, false);
    }

    public Member(Long id, String username, String password, String name, String phone, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
