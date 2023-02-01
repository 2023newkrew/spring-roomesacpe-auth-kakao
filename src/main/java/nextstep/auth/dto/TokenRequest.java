package nextstep.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.member.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

    private String id;
    private String role;

    public TokenRequest(Long id, String role) {
        this.id = String.valueOf(id);
        this.role = role;
    }

    public TokenRequest(Member member) {
        this.id = String.valueOf(member.getId());
        this.role = member.getRole();
    }

    public static TokenRequest fromMember(Member member) {
        return new TokenRequest(member.getId(), member.getRole());
    }
}
