package nextstep.member.domain;

import lombok.Getter;

@Getter
public class LoginMember {
    private final Long id;

    public LoginMember(Member member) {
        this.id = member.getId();
    }
}
