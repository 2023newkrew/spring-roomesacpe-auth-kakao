package nextstep.member;

import nextstep.exceptions.exception.notFound.MemberNotFoundException;
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

    public Member findById(Long id) {
        return memberDao.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    public Member findByUsername(String name) {
        return memberDao.findByUsername(name).orElseThrow(MemberNotFoundException::new);
    }

    public void deleteById(Long id) {
        memberDao.findById(id).orElseThrow(MemberNotFoundException::new);
        memberDao.deleteById(id);
    }

}
