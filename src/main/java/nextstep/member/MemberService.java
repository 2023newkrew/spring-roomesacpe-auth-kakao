package nextstep.member;

import nextstep.support.DuplicateEntityException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        if (hasDuplicatedUserName(memberRequest.getUsername())) {
            throw new DuplicateEntityException();
        }

        return memberDao.save(memberRequest.toEntity());
    }

    private boolean hasDuplicatedUserName(String username) {
        return memberDao.findByUsername(username).isPresent();
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }
}
