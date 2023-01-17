package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public MemberService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }


    public Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new NotExistEntityException();
        }
        return member;
    }

    public TokenResponse generateToken(TokenRequest tokenRequest) {
        validateIsMember(tokenRequest);
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }

    public void validateIsMember(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        boolean memberResult = memberDao.isMember(username, password);
        if (!memberResult) {
            throw new NotExistEntityException();
        }
    }
}
