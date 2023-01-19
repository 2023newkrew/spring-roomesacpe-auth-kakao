package nextstep.auth;

import nextstep.global.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
@Sql("/truncate.sql")
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
}