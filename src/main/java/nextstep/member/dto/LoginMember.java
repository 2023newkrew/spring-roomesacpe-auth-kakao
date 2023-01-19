package nextstep.member.dto;

import nextstep.member.MemberRole;

public class LoginMember extends MemberBaseDto {
    public LoginMember(Long id, String username, String name, MemberRole memberRole) {
        super(id, username, name, memberRole);
    }

}
