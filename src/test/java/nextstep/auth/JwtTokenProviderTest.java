package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    public final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    private final AuthMemberDTO authMemberDTO = new AuthMemberDTO();

    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken(authMemberDTO);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken(authMemberDTO);

        assertThat(jwtTokenProvider.getAuthMember(token)).isEqualTo(authMemberDTO);
    }
}
