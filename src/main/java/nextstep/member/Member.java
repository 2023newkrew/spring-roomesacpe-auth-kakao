package nextstep.member;

import lombok.*;

import static nextstep.member.MemberRole.USER;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
    private MemberRole role;

    public Member(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = USER;
    }

    public Member(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = USER;
    }

    public Member(Long id, String username, String password, String name, String phone, MemberRole role) {
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
