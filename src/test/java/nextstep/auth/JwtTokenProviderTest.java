package nextstep.auth;

import io.jsonwebtoken.JwtException;
import nextstep.support.exception.NoAccessTokenException;
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

    @Test
    @DisplayName("액세스 토큰이 유효하지 않다면 JwtException 발생")
    void getPrincipalThrowJwtExceptionTest() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        assertThatThrownBy(() -> jwtTokenProvider.getPrincipal("token"))
                .isInstanceOf(JwtException.class);
    }

    @Test
    @DisplayName("액세스 토큰이 비어있다면 NoAccessTokenException 발생")
    void getPrincipalThrowNoAccessTokenExceptionTest(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        assertThatThrownBy(() -> jwtTokenProvider.getPrincipal(""))
                .isInstanceOf(NoAccessTokenException.class);
    }
}
