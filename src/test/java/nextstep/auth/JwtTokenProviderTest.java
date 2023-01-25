package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes={JwtTokenProvider.class})
class JwtTokenProviderTest {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken("1", Authority.USER);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken("1", Authority.USER);

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }
}