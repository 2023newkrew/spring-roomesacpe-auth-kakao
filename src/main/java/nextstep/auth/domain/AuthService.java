package nextstep.auth.domain;

import nextstep.auth.util.JwtTokenProvider;
import nextstep.member.domain.Member;
import nextstep.support.NotExistEntityException;
import nextstep.support.NotValidateTokenException;
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
        return jwtTokenProvider.createToken(member.getId().toString());
    }

    public String getPrincipal(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new NotValidateTokenException();
        }
        return jwtTokenProvider.getPrincipal(accessToken);
    }
}
