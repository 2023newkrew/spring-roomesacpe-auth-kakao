package nextstep.auth;

import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.exception.NotCorrectPasswordException;
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
                .orElseThrow(() -> new NotExistEntityException(ErrorCode.MEMBER_NOT_FOUND));
        if(member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new NotCorrectPasswordException(ErrorCode.UNAUTHORIZED, "잘못된 ID 또는 비밀번호입니다.");
        }
        String accessToken = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(accessToken);
    }
}
