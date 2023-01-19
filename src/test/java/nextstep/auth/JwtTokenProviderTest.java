package nextstep.auth;

import nextstep.member.Role;
import nextstep.support.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    public static final JwtTokenProvider JWT_TOKEN_PROVIDER = new JwtTokenProvider();

    @Test
    @DisplayName("사용자 토큰 생성 성공 테스트")
    void createTokenSuccessTest() {
        String token = JWT_TOKEN_PROVIDER.createToken("username");
        assertThatCode(() -> JWT_TOKEN_PROVIDER.validateToken(token)).doesNotThrowAnyException();
        assertThat(JWT_TOKEN_PROVIDER.getPrincipal(token)).isEqualTo("username");
        assertThat(JWT_TOKEN_PROVIDER.getRole(token)).isEqualTo(Role.USER.name());
    }

    @Test
    @DisplayName("관리자 토큰 생성 테스트")
    void createAdminTokenTest(){
        String adminToken = JWT_TOKEN_PROVIDER.createAdminToken("adminName");
        assertThatCode(() -> JWT_TOKEN_PROVIDER.validateToken(adminToken)).doesNotThrowAnyException();
        assertThat(JWT_TOKEN_PROVIDER.getPrincipal(adminToken)).isEqualTo("adminName");
        assertThat(JWT_TOKEN_PROVIDER.getRole(adminToken)).isEqualTo(Role.ADMIN.name());
    }

    @Test
    void getPrincipal() {
        String token = JWT_TOKEN_PROVIDER.createToken("1");

        assertThat(JWT_TOKEN_PROVIDER.getPrincipal(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("액세스 토큰이 유효하지 않다면 UnauthorizedException 발생")
    void getPrincipalThrowJwtExceptionTest() {
        assertThatThrownBy(() -> JWT_TOKEN_PROVIDER.getPrincipal("token"))
                .isInstanceOf(UnauthorizedException.class);

        assertThatThrownBy(() -> JWT_TOKEN_PROVIDER.getPrincipal(""))
                .isInstanceOf(UnauthorizedException.class);
    }
}
