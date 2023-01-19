package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.excpetion.NotCorrectPasswordException;
import nextstep.support.excpetion.NotExistMemberException;
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
                .orElseThrow(NotExistMemberException::new);
        if(member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new NotCorrectPasswordException();
        }
        String accessToken = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(accessToken);
    }
}
