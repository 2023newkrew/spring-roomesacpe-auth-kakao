package nextstep.member;

import nextstep.infrastructure.auth.MemberDetails;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public MemberDetails memberToMemberDetails(Member member) {
        return MemberDetails.builder()
                .id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .password(member.getPassword())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }
}
