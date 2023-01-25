package nextstep.member;

import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Integer role;

    public Member(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = 1;
    }

    public Member(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = 1;
    }

    public Member(Long id, String username, String password, String name, String phone, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
