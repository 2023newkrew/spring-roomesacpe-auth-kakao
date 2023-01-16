package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        Member member = login(username, password);

        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(member.getId()), new ArrayList<>()));
    }

    private Member login(String username, String password) {
        Member member = memberDao.findByUsername(username);
        if (member.isWrongPassword(password)) {
            throw new AuthenticationException();
        }
        return member;
    }
}
