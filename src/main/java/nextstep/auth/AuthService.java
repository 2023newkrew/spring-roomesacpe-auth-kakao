package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.AuthorizationException;
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
        Member member = retrieveMember(tokenRequest.getUsername());
        checkAuthentication(member, tokenRequest);

        UserPrincipal principal = new UserPrincipal(member.getUsername(), member.getRole());
        String accessToken = jwtTokenProvider.createToken(principal);
        return new TokenResponse(accessToken);
    }

    private Member retrieveMember(String username) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new AuthorizationException();
        }
        return member;
    }

    private void checkAuthentication(Member member, TokenRequest tokenRequest) {
        if (!member.getPassword().equals(tokenRequest.getPassword())) {
            throw new AuthorizationException();
        }
    }
}
