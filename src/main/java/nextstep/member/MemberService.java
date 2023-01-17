package nextstep.member;

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

    public MemberResponse findById(Long id) {
        return new MemberResponse(memberDao.findById(id));
    }

    public MemberResponse findByUsername(String name) {
        return new MemberResponse(memberDao.findByUsername(name));
    }
}
