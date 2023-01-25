package nextstep.member;

import nextstep.auth.Authority;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity(Authority.USER));
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }
}
