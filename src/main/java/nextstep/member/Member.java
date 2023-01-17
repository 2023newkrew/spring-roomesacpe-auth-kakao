package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.permission.Permission;

@NoArgsConstructor // 테스트 시 Serializable 관련 이슈로 추가
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Permission permission;
    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
