package nextstep.auth;

import nextstep.infrastructure.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {
    private static final String SECRET_KEY = "secretKey";
    private static final Long MAX_AGE = 32000L;

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, MAX_AGE);

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, MAX_AGE);

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }
}