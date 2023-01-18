package nextstep.member;

public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String role;

    public MemberRequest(String username, String password, String name, String phone, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public MemberRequest() {

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
        return role;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .phone(phone)
                .role(Role.map.get(role))
                .build();
    }
}
