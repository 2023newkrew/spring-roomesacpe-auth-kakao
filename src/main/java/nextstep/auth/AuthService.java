package nextstep.auth;

import nextstep.member.Member;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createAccessToken(Member member, String password) {
        if (member.checkWrongPassword(password)) {
            throw new NotExistEntityException();
        }
        return jwtTokenProvider.createToken(member.getUsername());
    }

    public String getPrincipal(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new RuntimeException();
        }
        return jwtTokenProvider.getPrincipal(accessToken);
    }
}
