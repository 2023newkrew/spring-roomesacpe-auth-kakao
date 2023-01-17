package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;
import nextstep.support.AuthorizationException;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public boolean checkInvalidLogin(String principal, String credentials, Member member) {

        return !member.getUsername().equals(principal) || !member.getPassword().equals(credentials);
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (checkInvalidLogin(tokenRequest.getUsername(), tokenRequest.getPassword(), member)) {
            throw new AuthorizationException();
        }

        String accessToken = jwtTokenProvider.createToken(member.getId().toString());
        return new TokenResponse(accessToken);
    }

}
