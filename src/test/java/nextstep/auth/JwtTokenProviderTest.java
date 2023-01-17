package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    private static final String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNSIsImlhdCI6MTY3Mzg1OTIwMSwiZXhwIjoxNjczODYyODAxfQ.dBsCm7PImHV5D78C6lt3UYOPbWo2VWklodMvGXYsdZs";
    private static final String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0";

    @DisplayName("새로운 토큰을 생성한다.")
    @Test
    void createToken() {
        String token = JwtTokenProvider.createToken("1");

        assertThat(JwtTokenProvider.validateToken(token)).isTrue();
    }

    @DisplayName("토큰에서 member_id를 가져온다.")
    @Test
    void getPrincipal() {
        String token = JwtTokenProvider.createToken("1");

        assertThat(JwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }

    @DisplayName("만료된 토큰을 검증한다.")
    @Test
    void validateExpiredToken() {
        assertThat(JwtTokenProvider.validateToken(expiredToken))
                .isFalse();
    }

    @DisplayName("올바르지 않은 포맷의 토큰을 검증한다.")
    @Test
    void validateInvalidToken() {
        assertThat(JwtTokenProvider.validateToken(invalidToken))
                .isFalse();
    }
}