package nextstep.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberCreateRequest memberCreateRequest) {
        return memberDao.save(memberCreateRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member authorization(MemberAuthorizationRequest memberAuthorizationRequest) {
        memberDao.updateRole(memberAuthorizationRequest.getId(), MemberRole.ADMIN);
        return memberDao.findById(memberAuthorizationRequest.getId());
    }
}
