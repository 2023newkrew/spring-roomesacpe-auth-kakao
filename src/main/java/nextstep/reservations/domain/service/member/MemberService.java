package nextstep.reservations.domain.service.member;

import nextstep.reservations.dto.member.MemberRequestDto;
import nextstep.reservations.repository.member.MemberRepository;
import nextstep.reservations.util.mapper.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberService(final MemberRepository memberRepository, final MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public Long addMember(final MemberRequestDto memberRequestDto) {
        Long memberId = memberRepository.add(memberMapper.requestDtoToMember(memberRequestDto));
        return memberId;
    }
}
