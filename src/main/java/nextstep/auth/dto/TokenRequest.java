package nextstep.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.member.Member;
import nextstep.member.Role;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

    private String id;
    private Role role;

    public TokenRequest(Long id, String role) {
        this(String.valueOf(id), Role.from(role));
    }

    public TokenRequest(Long id, Role role) {
        this(String.valueOf(id), role);
    }

    public TokenRequest(Member member) {
        this(String.valueOf(member.getId()), member.getRole());
    }

    public static TokenRequest fromMember(Member member) {
        return new TokenRequest(member);
    }
}
