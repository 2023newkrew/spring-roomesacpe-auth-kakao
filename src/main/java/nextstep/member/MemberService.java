package nextstep.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberDao memberDao) {
        this.memberRepository = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberRepository.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }
}
