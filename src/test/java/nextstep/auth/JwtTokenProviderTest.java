package nextstep.auth;

import nextstep.member.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    @DisplayName("토큰이 정상적으로 생성된다.")
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("Principal 값을 정상적으로 추출한다.")
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = jwtTokenProvider.createToken("1");

        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("지정한 비공개 클레임(Role)을 정상적으로 추출한다.")
    void getRole() {
        //given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String principal = "admin";
        Map<String, String> map = Map.of("role", Role.ADMIN.name());

        //when
        String token = jwtTokenProvider.createToken(principal, map);

        //then
        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(principal);
        assertThat(jwtTokenProvider.getRole(token).isPresent()).isTrue();
        assertThat(jwtTokenProvider.getRole(token).get()).isEqualTo(Role.ADMIN.name());
    }

    @Test
    @DisplayName("비공개 클레임(Role)을 지정하지 않았을 때 Optional Empty가 반환된다.")
    void getRole_null() {
        //given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String principal = "admin";

        //when
        String token = jwtTokenProvider.createToken(principal);

        //then
        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(principal);
        assertThat(jwtTokenProvider.getRole(token).isEmpty()).isTrue();
    }
}