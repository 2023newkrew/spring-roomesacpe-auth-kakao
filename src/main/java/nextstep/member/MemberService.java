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

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUserName(String username) {
        return memberDao.findByUsername(username);
    }

    public void updateUserToAdmin(String username) {
        memberDao.updateRoleByUsername(username, "admin");
    }

    public void updateAdminToUser(String username) {
        memberDao.updateRoleByUsername(username, "user");
    }
}
