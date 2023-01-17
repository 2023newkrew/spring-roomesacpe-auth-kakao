package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }

    // TODO 테스트 개선(Exception마다 상세하게)
    @Test
    void getPrincipalThrowExceptionTest() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        assertThatThrownBy(() -> jwtTokenProvider.getPrincipal("token"))
                .isInstanceOf(RuntimeException.class);
    }
}
