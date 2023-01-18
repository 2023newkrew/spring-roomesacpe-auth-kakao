package nextstep.member;

import nextstep.dto.response.MemberResponse;

public class MemberResponseConverter {

    public static MemberResponse memberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }

}
