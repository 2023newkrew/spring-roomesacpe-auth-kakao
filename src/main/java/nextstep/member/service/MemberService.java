package nextstep.member.service;

import nextstep.member.datamapper.MemberMapper;
import nextstep.member.domain.Member;
import nextstep.member.dto.MemberResponse;
import nextstep.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long create(String username, String password, String name, String phone) {
        Member requestedMember = new Member(username, password, name, phone);
        requestedMember.encryptPassword();

        return memberRepository.save(requestedMember);
    }

    public MemberResponse findById(Long id) {

        return memberRepository.findById(id)
                .map(MemberMapper.INSTANCE::entityToResponseDto)
                .orElse(null);
    }
}
