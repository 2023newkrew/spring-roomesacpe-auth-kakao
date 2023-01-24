package nextstep.auth;

import nextstep.domain.member.MemberRole;
import nextstep.service.LoginService;
import nextstep.utils.JwtTokenProvider;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import nextstep.error.ApplicationException;
import nextstep.domain.member.Member;
import nextstep.domain.member.MemberDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static nextstep.error.ErrorType.MEMBER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private MemberDao memberDao;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private LoginService loginService;

    @Test
    void 아이디에_해당하는_사용자가_존재하지_않을_경우_예외가_발생한다() {
        // given
        String wrongUsername = "wrongUsername", password = "1234";
        TokenRequest tokenRequest = new TokenRequest(wrongUsername, password);

        given(memberDao.findByUsername(wrongUsername))
                .willThrow(new ApplicationException(MEMBER_NOT_FOUND));

        // when, then
        assertThatThrownBy(() -> loginService.login(tokenRequest))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void 사용자의_비밀번호가_일치하지_않을_경우_예외가_발생한다() {
        // given
        String username = "username", password = "password", wrongPassword = "wrongPassword";
        TokenRequest tokenRequest = new TokenRequest(username, wrongPassword);
        Member member = new Member(username, password, "name", "010-0000-0000", MemberRole.USER);

        given(memberDao.findByUsername(member.getUsername()))
                .willReturn(member);

        // when, then
        assertThatThrownBy(() -> loginService.login(tokenRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 아이디와_비밀번호가_일치할_경우_토큰이_반환된다() {
        // given
        String username = "username", password = "password", token = "access-token";
        TokenRequest tokenRequest = new TokenRequest(username, password);
        Member member = new Member(1L, username, password, "name", "010-0000-0000", MemberRole.USER);

        given(memberDao.findByUsername(member.getUsername()))
                .willReturn(member);
        given(jwtTokenProvider.createToken(member.getId().toString(), member.getRole().name()))
                .willReturn(token);

        // when
        TokenResponse tokenResponse = loginService.login(tokenRequest);

        assertThat(tokenResponse.getAccessToken()).isEqualTo(token);
    }

}
