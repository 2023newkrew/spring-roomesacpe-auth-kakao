package nextstep.auth;

import nextstep.persistence.member.Role;
import nextstep.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    private static final String PRINCIPAL = "1";

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(PRINCIPAL, Role.NORMAL);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(PRINCIPAL, Role.NORMAL);

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(PRINCIPAL, Role.NORMAL);
    }
}