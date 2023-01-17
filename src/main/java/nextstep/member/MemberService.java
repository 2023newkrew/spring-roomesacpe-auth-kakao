package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MemberDao memberDao;

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

    public Member findByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return findByUsername(payload);
    }
}
