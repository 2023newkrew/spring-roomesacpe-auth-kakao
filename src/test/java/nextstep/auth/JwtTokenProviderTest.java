package nextstep.auth;

import nextstep.auth.utils.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.dto.LoginMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(new Member(1L,"123","456","789","123123"));

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(new Member(1L,"123","456","789","123123"));

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(new LoginMember(1L, "123", "789"));
    }
}