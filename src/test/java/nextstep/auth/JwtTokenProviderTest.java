package nextstep.auth;

import nextstep.framework.auth.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "aEdGbPAF9uZgU5jo6lis2xfJI53sgwgVFKGrEpcaBug=");
    }

    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken("1", "USER");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken("1", "USER");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }
}