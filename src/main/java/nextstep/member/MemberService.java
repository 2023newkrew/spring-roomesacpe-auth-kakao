package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new NullPointerException("유저가 존재하지 않습니다.");
        }
        return member;
    }

    public Member findByToken(String token) {
        String principal = jwtTokenProvider.getPrincipal(token);
        return findByUsername(principal);
    }
}
