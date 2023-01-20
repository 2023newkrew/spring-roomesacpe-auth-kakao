package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.excpetion.NotCorrectUserNameOrPasswordException;
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
                .orElseThrow(NotCorrectUserNameOrPasswordException::new);
        if(member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new NotCorrectUserNameOrPasswordException();
        }
        String accessToken = jwtTokenProvider.createToken(member.getUsername(), member.getId());
        return new TokenResponse(accessToken);
    }
}
