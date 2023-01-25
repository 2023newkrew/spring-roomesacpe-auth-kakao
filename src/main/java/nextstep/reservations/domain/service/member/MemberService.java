package nextstep.reservations.domain.service.member;

import nextstep.reservations.domain.entity.member.Member;
import nextstep.reservations.dto.member.MemberRequestDto;
import nextstep.reservations.exceptions.member.exception.NotExistMemberException;
import nextstep.reservations.repository.member.MemberRepository;
import nextstep.reservations.util.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Member findByUsername(final String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isEmpty()) {
            throw new NotExistMemberException();
        }
        return member.get();
    }
}
