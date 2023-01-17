package nextstep.member.domain;

import lombok.Getter;

@Getter
public class Member extends MemberForAuth {

    protected final String name;
    protected final String phone;

    public Member(String username, String password, String name, String phone) {
        super(username, password);
        this.name = name;
        this.phone = phone;
    }
}
