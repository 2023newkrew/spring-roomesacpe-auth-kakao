package nextstep.auth;

import io.jsonwebtoken.JwtException;
import nextstep.support.exception.NoAccessTokenException;
import nextstep.support.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    @DisplayName("토큰 생성 성공 테스트")
    void createTokenSuccessTest() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1");
        assertThatCode(() -> jwtTokenProvider.validateToken(token)).doesNotThrowAnyException();
    }


    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("액세스 토큰이 유효하지 않다면 UnauthorizedException 발생")
    void getPrincipalThrowJwtExceptionTest() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        assertThatThrownBy(() -> jwtTokenProvider.getPrincipal("token"))
                .isInstanceOf(UnauthorizedException.class);

        assertThatThrownBy(() -> jwtTokenProvider.getPrincipal(""))
                .isInstanceOf(UnauthorizedException.class);
    }
}
