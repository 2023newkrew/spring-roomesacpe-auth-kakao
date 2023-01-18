package nextstep.auth;

import nextstep.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(new TokenData(1L, MemberRole.NORMAL.toString()));

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(new TokenData(1L, MemberRole.NORMAL.toString()));

        assertThat(jwtTokenProvider.getTokenData(token)
                           .getId()).isEqualTo(1L);
    }
}