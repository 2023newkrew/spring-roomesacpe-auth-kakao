package nextstep.member;

import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        if (!memberDao.findByUsername(memberRequest.getUsername()).isEmpty()){
            throw new KeyAlreadyExistsException("Already Registered User");
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public List<Member> findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

}
