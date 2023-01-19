package nextstep.member;

import nextstep.exceptions.exception.ObjectNotFoundException;
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

    public MemberResponse findById(Long id) {
        return new MemberResponse(notNullableMember(memberDao.findById(id)));
    }

    public MemberResponse findByUsername(String name) {
        return new MemberResponse(notNullableMember(memberDao.findByUsername(name)));
    }

    private Member notNullableMember(Member member) {
        if (member == null) {
            throw new ObjectNotFoundException("해당 멤버를 찾을 수 없습니다.");
        }
        return member;
    }
}
