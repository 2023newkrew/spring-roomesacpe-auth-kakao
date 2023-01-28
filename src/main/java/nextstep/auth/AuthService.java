package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRole;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new AuthenticationException();
        }
        MemberRole role = member.getRole();
        TokenData tokenData = new TokenData(member.getId(), role.toString());
        return new TokenResponse(jwtTokenProvider.createToken(tokenData));
    }
}
