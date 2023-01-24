package nextstep.auth;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenExtractor {
    public String extractToken(String tokenWithClass) {
        if (tokenWithClass != null) {
            return tokenWithClass.replace(JwtTokenConfig.TOKEN_CLASS, "");
        }
        return null;
    }
}
