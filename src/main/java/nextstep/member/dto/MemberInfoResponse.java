package nextstep.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.member.Member;

public class MemberInfoResponse {

    private final String name;
    private final String username;
    private final String phone;

    @JsonCreator
    public MemberInfoResponse(String name, String username, String phone) {
        this.name = name;
        this.username = username;
        this.phone = phone;
    }

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getName(), member.getUsername(), member.getPhone());
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }
}
