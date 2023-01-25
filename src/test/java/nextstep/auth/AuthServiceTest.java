package nextstep.auth;

import nextstep.auth.dto.TokenRequestDto;
import nextstep.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("[일반 고객]TokenRequest가 넘어오면 JWT를 반환한다.")
    @Test
    void loginTest() {
        // given
        TokenRequestDto tokenRequestDto = new TokenRequestDto(USERNAME, PASSWORD, MemberRole.GENERAL.getName());

        when(jwtTokenProvider.createToken(tokenRequestDto)).thenReturn(TOKEN);

        assertThat(authService.login(tokenRequestDto)).isEqualTo(TOKEN);
    }

    @DisplayName("토큰을 이용하여 username을 구한다.")
    @Test
    void findUsernameByTokenTest() {
        when(jwtTokenProvider.getUsername(anyString())).thenReturn(USERNAME);

        assertThat(authService.findUsernameByToken(TOKEN)).isEqualTo(USERNAME);
    }
}
