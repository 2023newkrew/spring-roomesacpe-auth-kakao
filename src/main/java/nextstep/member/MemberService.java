package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import org.springframework.stereotype.Service;;

import java.util.Optional;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Optional<Member> findById(Long id) {
        return memberDao.findById(id);
    }

    public Optional<Member> findMemberByToken(String token) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        if(!jwtTokenProvider.validateToken(token)){
            // TODO: throw exception. Token invalid
            return Optional.empty();
        }
        String payload = jwtTokenProvider.getPrincipal(token);
        return findMember(payload);
    }

    private Optional<Member> findMember(String principal) {
        return memberDao.findByUsername(principal);
    }
}
