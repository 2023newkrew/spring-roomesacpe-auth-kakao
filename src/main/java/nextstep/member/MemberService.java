package nextstep.member;

import nextstep.common.Role;
import nextstep.exception.NotExistEntityException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Member findByNameAndPassword(String username, String password) {
        return this.memberDao.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new NotExistEntityException("해당하는 정보의 사용자가 존재하지 않습니다."));
    }

    public Long create(Member member) {
        return memberDao.save(member);
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }

    public Member findById(Long id) {
        return memberDao.findById(id)
                .orElseThrow( () -> new NotExistEntityException("존재하지 않는 아이디 입니다."));
    }

    public Member findByUsernameAndPassword(String username, String password) {
        return memberDao.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new NotExistEntityException("해당하는 정보의 사용자가 존재하지 않습니다."));
    }

    public Member findAndUpdateRole(Long id, Role role) {
        memberDao.updateRoleById(id, role);
        return findById(id);
    }
}
