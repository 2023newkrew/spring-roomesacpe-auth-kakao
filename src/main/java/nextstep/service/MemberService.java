package nextstep.service;

import nextstep.dao.MemberDao;
import nextstep.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(Member member) {
        return memberDao.save(member);
    }

    public Member findByMemberId(Long id) {
        return memberDao.findByMemberId(id);
    }
}
