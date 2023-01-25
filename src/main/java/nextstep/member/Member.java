package nextstep.member;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Member {

    public static final String DEFAULT_ROLE = "user";

    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private Role role;

    public Member(String username, String password, String name, String phone) {
        this(null, username, password, name, phone, DEFAULT_ROLE);
    }

    public Member(Long id, String username, String password, String name, String phone) {
        this(id, username, password, name, phone, DEFAULT_ROLE);
    }

    public Member(String username, String password, String name, String phone, String role) {
        this(null, username, password, name, phone, role);
    }

    public Member(Long id, String username, String password, String name, String phone, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = Role.from(role);
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
        return role.toString();
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
