package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.exception.InvalidLoginException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(InvalidLoginException::new);

        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new InvalidLoginException();
        }

        String token = jwtTokenProvider.createToken(String.valueOf(member.getId()));
        return new TokenResponse(token);
    }
}