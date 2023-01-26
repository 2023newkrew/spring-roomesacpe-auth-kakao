package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.common.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private static final Member findMember = new Member("username", "password", "name", "010", MemberRole.GENERAL.toString());
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private MemberDao memberDao;

    @DisplayName("[일반 고객]TokenRequest가 넘어오면 JWT를 반환한다.")
    @Test
    void loginTest() {
        // given
        TokenRequestDto tokenRequestDto = new TokenRequestDto(USERNAME, PASSWORD, MemberRole.GENERAL.toString());

        when(jwtTokenProvider.createToken(tokenRequestDto)).thenReturn(TOKEN);

        assertThat(authService.login(tokenRequestDto)).isEqualTo(TOKEN);
    }

    @DisplayName("토큰을 이용하여 username을 구한다.")
    @Test
    void findUsernameByTokenTest() {
        when(jwtTokenProvider.getUsername(anyString())).thenReturn(USERNAME);

        assertThat(authService.findUsernameByToken(TOKEN)).isEqualTo(USERNAME);
    }

    @DisplayName("[성공] 로그인 정보를 이용하여 사용자의 유무를 체크한다.")
    @Test
    void validateUsernameAndPasswordTest() {
        when(memberDao.findByUsername(any())).thenReturn(Optional.of(findMember));
        TokenRequestDto tokenRequestDto = new TokenRequestDto(findMember.getUsername(), findMember.getPassword(),
            findMember.getRole().toString());

        assertThatCode(() -> authService.validateUsernameAndPassword(tokenRequestDto)).doesNotThrowAnyException();
    }

    @DisplayName("[잘못된 이름] 로그인 정보를 이용하여 사용자의 유무를 체크한다.")
    @Test
    void validateUsernameAndPasswordTest_wrongUsername() {
        when(memberDao.findByUsername(any())).thenReturn(Optional.empty());
        TokenRequestDto tokenRequestDto = new TokenRequestDto(findMember.getUsername(), findMember.getPassword(),
            findMember.getRole().toString());

        assertThatThrownBy(() -> authService.validateUsernameAndPassword((tokenRequestDto))).isInstanceOf(NotExistEntityException.class);
    }

    @DisplayName("[잘못된 비밀번호] 로그인 정보를 이용하여 사용자의 유무를 체크한다.")
    @Test
    void validateUsernameAndPasswordTest_wrongPassword() {
        when(memberDao.findByUsername(any())).thenReturn(Optional.of(findMember));
        TokenRequestDto tokenRequestDto = new TokenRequestDto(findMember.getUsername(), "wrong password",
            findMember.getRole().toString());

        assertThatThrownBy(() -> authService.validateUsernameAndPassword((tokenRequestDto))).isInstanceOf(NotExistEntityException.class);
    }

    @DisplayName("[잘못된 권한] 로그인 정보를 이용하여 사용자의 유무를 체크한다.")
    @Test
    void validateUsernameAndPasswordTest_wrongRole() {
        when(memberDao.findByUsername(any())).thenReturn(Optional.of(findMember));
        TokenRequestDto tokenRequestDto = new TokenRequestDto(findMember.getUsername(), "wrong password",
            MemberRole.ADMIN.toString());

        assertThatThrownBy(() -> authService.validateUsernameAndPassword((tokenRequestDto))).isInstanceOf(NotExistEntityException.class);
    }
}
