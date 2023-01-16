package nextstep.member;

import nextstep.support.NotExistEntityException;
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

    public Member findByUsername(String username) {
        try {
            return memberDao.findByUsername(username);
        } catch (RuntimeException e) {
            throw new NotExistEntityException();
        }
    }
}
