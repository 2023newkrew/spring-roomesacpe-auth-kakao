package nextstep.auth;

import nextstep.common.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(2515L, Role.MEMBER);

        assertThat(jwtTokenProvider.isValidToken(token)).isTrue();
    }

    @Test
    void getClaims() {
        Long id = 611L;
        Role role = Role.MEMBER;
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(id, role);

        assertThat(jwtTokenProvider.getClaims(token).get("id", Long.class)).isEqualTo(id);
        assertThat(jwtTokenProvider.getClaims(token).get("role", String.class)).isEqualTo("MEMBER");
    }
}