package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {
    @DisplayName("토큰 생성이 유요하게 이루어져야 한다")
    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1", "ADMIN");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("토큰의 memberId 가 조회되어야 한다")
    @Test
    void getMemberId() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1", "ADMIN");

        assertThat(jwtTokenProvider.getMemberId(token)).isEqualTo("1");
    }

    @DisplayName("토큰의 role 이 조회되어야 한다")
    @Test
    void getRole() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1", "ADMIN");

        assertThat(jwtTokenProvider.getRole(token)).isEqualTo("ADMIN");
    }
}