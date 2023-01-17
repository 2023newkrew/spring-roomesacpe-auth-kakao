package nextstep.member;

import nextstep.exception.NotExistMemberException;
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

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUserName(String userName) {
        return memberDao.findByUsername(userName).orElseThrow(NotExistMemberException::new);
    }
}
