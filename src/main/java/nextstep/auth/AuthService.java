package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse getToken(TokenRequest request) {
        Member member = memberDao.findByUsername(request.getUsername());
        if (member.getPassword().equals(request.getPassword())) {
            return new TokenResponse(jwtTokenProvider.createToken(member.getId().toString()));
        }
        return null;
    }
}
