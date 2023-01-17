package nextstep.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.member.Member;

public class MemberResponse extends MemberBaseDto {
    private final String password;
    private final String phone;

    private MemberResponse(Long id, String username, String password, String name, String phone) {
        super(id, username, name);
        this.password = password;
        this.phone = phone;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(),
                member.getPassword(), member.getName(), member.getPhone());
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
