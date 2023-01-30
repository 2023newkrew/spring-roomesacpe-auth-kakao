package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @DisplayName("토큰 생성 테스트")
    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1", false);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("Principal 테스트")
    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1", false);

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }

    @DisplayName("어드민 테스트")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isAdmin(Boolean booleans) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1", booleans);

        assertThat(jwtTokenProvider.isAdmin(token)).isEqualTo(booleans);
    }
}
