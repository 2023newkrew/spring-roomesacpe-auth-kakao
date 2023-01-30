package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("로그인 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private LoginService loginService;

    @DisplayName("username으로 사용자를 조회하는 기능 예외 발생")
    @Test
    void failToFindByUsername() {
        String username = "username", password = "password";
        TokenRequest tokenRequest = new TokenRequest(username, password);

        given(memberDao.findByUsername(username)).willThrow(RuntimeException.class);

        assertThatThrownBy(() -> loginService.createToken(tokenRequest)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void checkWrongPassword() {
        String username = "username", password = "password", wrongPassword = "wrongPassword";
        TokenRequest tokenRequest = new TokenRequest(username, wrongPassword);
        Member member = new Member(username, password, "name", "010-0000-0000");

        given(memberDao.findByUsername(member.getUsername()))
                .willReturn(member);

        assertThatThrownBy(() -> loginService.createToken(tokenRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("비밀번호가 일치할 경우 토큰이 반환된다.")
    @Test
    void returnToken() {
        String username = "username", password = "password", token = "TOKEN";
        TokenRequest tokenRequest = new TokenRequest(username, password);
        Member member = new Member(1L, username, password, "name", "010-0000-0000", false);

        given(memberDao.findByUsername(member.getUsername()))
                .willReturn(member);
        given(jwtTokenProvider.createToken("1", false)).willReturn(token);

        assertThat(loginService.createToken(tokenRequest).getAccessToken()).isEqualTo(token);
    }
}
