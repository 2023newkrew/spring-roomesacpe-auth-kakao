package nextstep.auth;

import nextstep.auth.dto.TokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken(new TokenRequest(1L, "ADMIN"));
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken(new TokenRequest(1L, "ADMIN"));
        assertThat(jwtTokenProvider.getSubject(token)).isEqualTo("1");
    }
}