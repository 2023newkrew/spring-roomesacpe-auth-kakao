package nextstep.auth;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenExtractor {
    public Optional<String> extractToken(String tokenWithClass) {
        if (tokenWithClass != null) {
            return Optional.of(tokenWithClass.replace(JwtTokenConfig.TOKEN_CLASS, ""));
        }
        return Optional.empty();
    }
}
