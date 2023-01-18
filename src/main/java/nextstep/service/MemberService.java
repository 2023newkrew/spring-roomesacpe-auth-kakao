package nextstep.service;

import nextstep.dto.request.MemberRequest;
import nextstep.domain.Member;
import nextstep.dao.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findByMemberId(Long id) {
        return memberDao.findByMemberId(id);
    }
}
