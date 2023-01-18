package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.JwtTokenProvider;
import nextstep.support.exception.NoSuchMemberException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

    public Member findByToken(String token){
        return findById(Long.valueOf(jwtTokenProvider.getPrincipal(token)));
    }
}
