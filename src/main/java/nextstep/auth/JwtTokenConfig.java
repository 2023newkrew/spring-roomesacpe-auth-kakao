package nextstep.auth;

public class JwtTokenConfig {
    public static final String SECRET_KEY = "learning-test-spring";
    public static final long VALIDITY_IN_MILLISECONDS = 3600000L;

    public static final String TOKEN_CLASS = "BEARER ";
}
