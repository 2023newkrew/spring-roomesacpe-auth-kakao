package nextstep.member;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MemberResponse extends MemberBaseDto {
    private String password;
    private String phone;

    @JsonCreator
    public MemberResponse(Long id, String username, String password, String name, String phone) {
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
