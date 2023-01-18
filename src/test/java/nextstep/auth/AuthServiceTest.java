package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("TokenRequest가 넘어오면 JWT를 반환한다.")
    void loginTest() {
        // given
        TokenRequestDto tokenRequestDto = new TokenRequestDto(USERNAME, PASSWORD);

        when(jwtTokenProvider.createToken(tokenRequestDto.getUsername())).thenReturn(TOKEN);

        assertThat(authService.login(tokenRequestDto)).isEqualTo(TOKEN);
    }

    @Test
    @DisplayName("토큰을 이용하여 username을 구한다.")
    void findUsernameByTokenTest() {
        when(jwtTokenProvider.getPrincipal(anyString())).thenReturn(USERNAME);

        assertThat(authService.findUsernameByToken(TOKEN)).isEqualTo(USERNAME);
    }
}
