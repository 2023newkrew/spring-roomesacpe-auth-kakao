package nextstep.member.domain;

import lombok.Getter;
import nextstep.member.domain.Member;

@Getter
public class LoginMember {
    private final Long id;

    public LoginMember(Member member) {
        this.id = member.getId();
    }
}
