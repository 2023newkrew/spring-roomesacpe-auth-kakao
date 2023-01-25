package nextstep.auth;

import nextstep.auth.dto.TokenRequestDto;
import nextstep.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    private static final TokenRequestDto tokenRequestDto = new TokenRequestDto("username",
        "password",
        MemberRole.GENERAL.getName());

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(tokenRequestDto);

        assertThat(jwtTokenProvider.getUsername(token)).isNotNull();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(tokenRequestDto);

        assertThat(jwtTokenProvider.getUsername(token)).isEqualTo(tokenRequestDto.getUsername());
    }

    // TODO 테스트 개선(Exception마다 상세하게)
    @Test
    void getPrincipalThrowExceptionTest() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        assertThatThrownBy(() -> jwtTokenProvider.getUsername("token"))
            .isInstanceOf(RuntimeException.class);
    }
}
