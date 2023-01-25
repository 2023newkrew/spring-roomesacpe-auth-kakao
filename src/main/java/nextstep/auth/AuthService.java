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
        Member member = memberDao.findByUsername(tokenRequest.getUsername());

        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        String accessToken = jwtTokenProvider.createToken(AuthMemberDTO.from(member));
        return new TokenResponse(accessToken);
    }
}
