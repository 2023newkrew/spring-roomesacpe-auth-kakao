package nextstep.domain.auth;

import nextstep.persistence.member.Member;
import nextstep.support.NotValidateTokenException;
import nextstep.util.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Optional<String> createAccessToken(Member member, String password) {
        if (member.checkWrongPassword(password)) {
            return Optional.empty();
        }
        return Optional.of(jwtTokenProvider.createToken(member.getId().toString(), member.getRole()));
    }

    public String getPrincipal(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new NotValidateTokenException();
        }
        return jwtTokenProvider.getPrincipal(accessToken);
    }

    public String get(String accessToken, String claim) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new NotValidateTokenException();
        }
        return jwtTokenProvider.get(accessToken, claim);
    }
}
