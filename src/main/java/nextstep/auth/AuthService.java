package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
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
            throw new AuthenticationException(ErrorCode.INVALID_USERNAME_PASSWORD);
        }

        return new TokenResponse(jwtTokenProvider.createToken(member.getId().toString(), null));
    }
}
