package nextstep.auth.domain;

import nextstep.auth.util.JwtTokenProvider;
import nextstep.member.domain.Member;
import nextstep.support.NotExistEntityException;
import nextstep.support.NotValidateTokenException;
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
        return Optional.of(jwtTokenProvider.createToken(member.getId().toString()));
    }

    public String getPrincipal(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new NotValidateTokenException();
        }
        return jwtTokenProvider.getPrincipal(accessToken);
    }
}
