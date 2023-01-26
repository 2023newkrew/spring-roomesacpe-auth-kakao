package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.auth.dto.TokenRequestDto;
import nextstep.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private static final TokenRequestDto tokenRequestDto = new TokenRequestDto("username",
        "password",
        MemberRole.GENERAL.toString());

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken(tokenRequestDto);
        assertThat(jwtTokenProvider.getUsername(token)).isEqualTo(tokenRequestDto.getUsername());
    }

    @Test
    void getPrincipalThrowExceptionTest() {
        assertThatThrownBy(() -> jwtTokenProvider.getUsername("token"))
            .isInstanceOf(RuntimeException.class);
    }
}
