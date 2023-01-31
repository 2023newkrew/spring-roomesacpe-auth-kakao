package nextstep.member;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.support.Messages.*;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Long create(MemberRequest memberRequest) {
        if (!memberDao.findByUsername(memberRequest.getUsername()).isEmpty()){
            throw new DuplicateKeyException(ALREADY_USER.getMessage());
        }
        Member member = new Member(memberRequest.getUsername(), passwordEncoder.encode(memberRequest.getPassword()),
                memberRequest.getName(), memberRequest.getPhone(), Role.MEMBER);
        return memberDao.save(member);
    }

    public List<Member> findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

    public void updateAdmin(MemberRequest memberRequest) {
        List<Member> memberList = memberDao.findByUsername(memberRequest.getUsername());
        if (memberList.isEmpty()) {
            throw new NullPointerException(MEMBER_NOT_FOUND.getMessage());
        }
        if (memberList.get(0).getRole() == Role.ADMIN){
            throw new DuplicateKeyException(ALREADY_ADMIN.getMessage());
        }
        memberDao.updateAdmin(memberList.get(0).getUsername());
    }
}
