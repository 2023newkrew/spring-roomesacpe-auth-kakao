package nextstep.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.member.Member;
import nextstep.member.MemberRole;

public class MemberResponse extends MemberBaseDto {
    private final String password;
    private final String phone;

    @JsonCreator
    public MemberResponse(Long id, String username, String password, String name, String phone, MemberRole role) {
        super(id, username, name,role);
        this.password = password;
        this.phone = phone;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(),
                member.getPassword(), member.getName(), member.getPhone(), member.getRole());
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
