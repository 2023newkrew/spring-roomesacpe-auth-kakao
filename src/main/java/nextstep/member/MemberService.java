package nextstep.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Long create(MemberCreateRequest memberCreateRequest) {
        MemberRole memberRole = MemberRole.USER;
        if (memberCreateRequest.getUsername().equals("admin") && memberCreateRequest.getPassword().equals("admin")) {
            memberRole = MemberRole.ADMIN;
        }
        Member member = new Member(
                memberCreateRequest.getUsername(),
                passwordEncoder.encode(memberCreateRequest.getPassword()),
                memberCreateRequest.getName(),
                memberCreateRequest.getPhone(),
                memberRole
        );
        return memberDao.save(member);
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member authorization(MemberAuthorizationRequest memberAuthorizationRequest) {
        memberDao.updateRole(memberAuthorizationRequest.getId(), MemberRole.ADMIN);
        return memberDao.findById(memberAuthorizationRequest.getId());
    }
}
