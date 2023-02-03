package nextstep.auth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nextstep.member.Member;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuthMemberDTO {

    private String username;

    private boolean isAdmin;

    public String getUsername() {
        return username;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public static AuthMemberDTO from(Member member) {
        return new AuthMemberDTO(member.getUsername(), member.isAdmin());
    }
}
