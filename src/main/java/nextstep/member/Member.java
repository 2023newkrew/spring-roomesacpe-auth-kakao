package nextstep.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member(String username, String password, String name, String phone) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
