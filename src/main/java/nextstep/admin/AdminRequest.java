package nextstep.admin;

public class AdminRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    public AdminRequest() {
    }

    public AdminRequest(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
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

    public Admin toEntity() {
        return new Admin(username, password, name, phone);
    }
}
