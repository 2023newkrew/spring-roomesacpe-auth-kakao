package nextstep.domain.member;

import nextstep.dto.member.MemberRequest;
import nextstep.persistence.member.Member;
import nextstep.persistence.member.MemberDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Optional<Member> findById(Long id) {
        return memberDao.findById(id);
    }

    public Optional<Member> findByUsername(String username) {
        return memberDao.findByUsername(username);
    }
}
