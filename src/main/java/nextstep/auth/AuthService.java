package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private boolean checkInvalidLogin(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        return member.checkWrongPassword(tokenRequest.getPassword());
    }
}
