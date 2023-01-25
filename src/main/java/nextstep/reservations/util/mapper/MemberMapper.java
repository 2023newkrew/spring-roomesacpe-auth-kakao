package nextstep.reservations.util.mapper;

import nextstep.reservations.domain.entity.member.Member;
import nextstep.reservations.dto.member.MemberRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member requestDtoToMember(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .username(memberRequestDto.getUsername())
                .password(memberRequestDto.getPassword())
                .name(memberRequestDto.getName())
                .phone(memberRequestDto.getPhone())
                .build();
    }
}
