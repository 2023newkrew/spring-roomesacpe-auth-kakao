package nextstep.member;

import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String role = Member.DEFAULT_ROLE;

    public MemberRequest(String username, String password, String name, String phone) {
        this(username, password, name, phone, Member.DEFAULT_ROLE);
    }

    public MemberRequest(String username, String password, String name, String phone, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
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

    public Member toEntity() {
        return new Member(username, password, name, phone, role);
    }
}
