package nextstep.auth;

import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.domain.context.MemberDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Test
    void createToken() {
        String token = jwtTokenProvider.createToken(MemberDetails.builder().build());

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        String token = jwtTokenProvider.createToken(MemberDetails.builder().id(1L).build());

        assertThat(jwtTokenProvider.getPrincipal(token).getId()).isEqualTo(1L);
    }
}