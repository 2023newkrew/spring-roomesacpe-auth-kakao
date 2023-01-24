package nextstep.domain.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import nextstep.domain.domain.Member;
import nextstep.domain.model.template.enumeration.Role;

public class MemberRequest {
    private final static String USER = "user";

    private String username;
    private String password;
    private String name;
    private String phone;
    @JsonIgnore
    private String role;

    public MemberRequest(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = USER;
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
        return new Member(username, password, name, phone, Role.of(role));
    }
}
