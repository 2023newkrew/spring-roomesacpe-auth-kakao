package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuthorizationTokenExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_TYPE = "Bearer";

    private AuthorizationTokenExtractor() {
    }

    public static Optional<String> extract(String authorization) {
        if (authorization == null) {
            return Optional.empty();
        }
        if (authorization.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return Optional.of(authorization.substring(BEARER_TYPE.length()).split(",")[0].trim());
        }
        return Optional.empty();
    }
}
