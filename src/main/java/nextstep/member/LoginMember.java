package nextstep.member;

import lombok.Getter;

@Getter
public class LoginMember {

    private Long id;
    private String name;
    public LoginMember(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }
}
