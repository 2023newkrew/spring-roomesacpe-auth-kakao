package nextstep.domain.mapper;

import nextstep.domain.context.MemberDetails;
import nextstep.domain.domain.Member;
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
