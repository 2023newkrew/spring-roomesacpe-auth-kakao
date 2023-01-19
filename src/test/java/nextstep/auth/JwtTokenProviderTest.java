package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.auth.utils.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberRole;
import nextstep.member.dto.LoginMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JwtTokenProvider 학습 테스트")
class JwtTokenProviderTest {

    @Test
    void createToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.createToken(
                Member.giveId(Member.builder()
                                .username("123")
                                .password("456")
                                .name("789")
                                .phone("123123")
                                .build(),
                        1L));
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getPrincipal() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.createToken(
                Member.giveId(Member.builder()
                                .username("123")
                                .password("456")
                                .name("789")
                                .phone("123123")
                                .build(),
                        1L));
        assertThat(jwtTokenProvider.getPrincipal(token)).isEqualTo(new LoginMember(1L, "123", "789", MemberRole.USER));
    }
}