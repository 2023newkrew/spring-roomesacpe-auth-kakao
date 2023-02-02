package nextstep.member;

import nextstep.type.UserType;

public class Member {
    private Long id;
    private final String username;
    private String password;
    private String name;
    private String phone;
    private UserType userType;

    public Member(Long id, String username, String password, String name, String phone, UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.userType = userType;
    }

    public Member(Long id, String username, String password, String name, String phone) {
        this(id, username, password, name, phone, UserType.NORMAL);
    }

    public Member(String username, String password, String name, String phone, UserType userType) {
        this(null, username, password, name, phone, userType);
    }

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone, UserType.NORMAL);
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

    public void changeType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
