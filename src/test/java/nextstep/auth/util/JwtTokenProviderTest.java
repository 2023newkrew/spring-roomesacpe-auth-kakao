package nextstep.auth.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    @DisplayName("토큰을 생성한다")
    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String input = "input";
        String token = jwtTokenProvider.createToken(input);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("토큰을 복호화한다")
    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String input = "input";
        String token = jwtTokenProvider.createToken(input);

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(input);
    }
}