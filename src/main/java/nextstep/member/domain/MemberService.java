package nextstep.member.domain;

import nextstep.member.dto.MemberRequest;
import nextstep.member.persistence.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }
}
