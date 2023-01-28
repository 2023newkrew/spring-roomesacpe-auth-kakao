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

//    private Member notNullableMember(Member member) {
//        if (member == null) {
//            throw new ObjectNotFoundException("해당 멤버를 찾을 수 없습니다.");
//        }
//        return member;
//    }
}
