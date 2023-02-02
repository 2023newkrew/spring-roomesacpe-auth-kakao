package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.UnAuthorizedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        validateUsernamePassword(member, tokenRequest.getPassword());

        String accessToken = jwtTokenProvider.createToken(member.getId()+"", member.getRole());
        return new TokenResponse(accessToken);
    }

    private void validateUsernamePassword(Member member, String password) throws UnAuthorizedException {
        if (member == null) {
            throw new UnAuthorizedException();
        }

        if (!Objects.equals(member.getPassword(), password)) {
            throw new UnAuthorizedException();
        }
    }
}
