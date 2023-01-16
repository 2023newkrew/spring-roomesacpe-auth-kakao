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

    public Member findByUsername(String username, String password) {
        Member member = memberDao.findByUsername(username);
        if (member.checkWrongPassword(password)) {
            throw new NotExistEntityException();
        }
        return member;
    }
}
