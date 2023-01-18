package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.Role;

public class TokenGenDto {
    private final Long id;
    private final Role role;

    public TokenGenDto(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public TokenGenDto(Member member) {
        this.id = member.getId();
        this.role = member.getRole();
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
