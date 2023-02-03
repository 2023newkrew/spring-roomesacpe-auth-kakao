package nextstep.auth;

import org.springframework.beans.factory.annotation.Value;

public class AuthContext {

    @Value("${security.secret-key}")
    public static final String SECRET_KEY = "learning-test-spring-learning-test-spring";
}
