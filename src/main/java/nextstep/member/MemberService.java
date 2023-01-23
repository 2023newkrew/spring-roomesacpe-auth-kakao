package nextstep.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

import static nextstep.RoomEscapeApplication.getPasswordEncoder;

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
        PasswordEncoder passwordEncoder = getPasswordEncoder();
        Member member = new Member(memberRequest.getUsername(), passwordEncoder.encode(memberRequest.getPassword()),
                memberRequest.getName(), memberRequest.getPhone());
        return memberDao.save(member);
    }

    public List<Member> findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

}
