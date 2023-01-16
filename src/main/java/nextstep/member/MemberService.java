package nextstep.member;

import nextstep.auth.TokenRequest;
import nextstep.support.NotExistEntityException;
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

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public void validateIsMember(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        boolean memberResult = memberDao.isMember(username, password);
        if (!memberResult) {
            throw new NotExistEntityException();
        }
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }
}
