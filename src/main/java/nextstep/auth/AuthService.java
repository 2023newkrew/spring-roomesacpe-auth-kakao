package nextstep.auth;

import nextstep.auth.util.JwtTokenProvider;
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
                .orElseThrow(() -> new NotExistEntityException("존재하지 않는 멤버입니다 - " + tokenRequest.getUsername()));
        if(member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new NotCorrectPasswordException();
        }
        String accessToken = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(accessToken);
    }
}
