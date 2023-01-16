package nextstep.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("TokenRequest가 넘어오면 JWT를 반환한다.")
    @Test
    void loginTest() {
        // given
        TokenRequestDto tokenRequestDto = new TokenRequestDto("username1", "password1");

        when(jwtTokenProvider.createToken(tokenRequestDto.getUsername())).thenReturn("token");

        assertThat(authService.login(tokenRequestDto)).isEqualTo("token");
    }
}
