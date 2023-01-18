package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.support.DuplicateEntityException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public Long create(MemberRequest memberRequest) {
        if(usernameExist(memberRequest)){
            throw new DuplicateEntityException();
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUserName(String name) {
        return memberDao.findByUsername(name);
    }

    private boolean usernameExist(MemberRequest memberRequest) {
        return findByUserName(memberRequest.getUsername()) != null;
    }
}
