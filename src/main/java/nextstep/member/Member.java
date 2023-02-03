package nextstep.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
public class Member {

    public static final String DEFAULT_ROLE = "user";

    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    @Getter(AccessLevel.NONE)
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

    @JsonIgnore
    public boolean isAdmin() {
        return this.role.isAdmin();
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    public String getRole() {
        return role.toString();
    }
}
