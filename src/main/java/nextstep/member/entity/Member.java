package nextstep.member.entity;

public class Member {

    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;

    public Member(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
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

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
