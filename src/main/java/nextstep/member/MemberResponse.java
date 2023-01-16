package nextstep.member;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MemberResponse extends MemberBaseDto {
    @JsonCreator
    public MemberResponse(Long id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    public static MemberResponse of(LoginMember loginMember) {
        return new MemberResponse(loginMember.getId(), loginMember.getUsername(),
                loginMember.getPassword(), loginMember.getName(), loginMember.getPhone());
    }
}
