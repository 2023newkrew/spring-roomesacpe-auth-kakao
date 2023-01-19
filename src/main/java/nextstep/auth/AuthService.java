package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.AuthorizationExcpetion;
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

        validateMember(tokenRequest.getUsername(), tokenRequest.getPassword());
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }

    void validateMember(String requestUsername, String requestPassword) {
        Member member = memberDao.findByUsername(requestUsername)
                .orElseThrow(() -> new AuthorizationExcpetion("계정 정보를 찾을 수 없습니다."));
        if (member.checkWrongPassword(requestPassword)) {
            throw new AuthorizationExcpetion("계정 정보를 찾을 수 없습니다.");
        }
    }
}
