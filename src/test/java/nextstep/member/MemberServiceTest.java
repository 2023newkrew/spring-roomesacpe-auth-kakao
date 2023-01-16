package nextstep.member;

import static org.assertj.core.api.Assertions.*;

import nextstep.auth.utils.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@DisplayName("멤버 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberDao memberDao;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("username으로 사용자를 조회하는 기능")
    @Test
    void findByUsername() {
        String username = "test";
        String password = "password";
        String name = "testName";
        String phone = "010-0000-0000";
        Member member = new Member(username, password, name, phone);

        given(memberDao.findByUsername(username)).willReturn(member);

        assertThat(memberService.findByUsername(username)).usingRecursiveComparison().isEqualTo(member);
    }

    @DisplayName("username으로 사용자를 조회하는 기능 예외 발생")
    @Test
    void failToFindByUsername() {
        String username = "test";

        given(memberDao.findByUsername(username)).willThrow(RuntimeException.class);

        assertThatThrownBy(() -> memberService.findByUsername(username)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void checkWrongPassword() {
        String username = "username", password = "password", wrongPassword = "wrongPassword";
        TokenRequest tokenRequest = new TokenRequest(username, wrongPassword);
        Member member = new Member(username, password, "name", "010-0000-0000");

        given(memberDao.findByUsername(member.getUsername()))
                .willReturn(member);

        assertThatThrownBy(() -> memberService.createToken(tokenRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("비밀번호가 일치할 경우 토큰이 반환된다.")
    @Test
    void returnToken() {
        String username = "username", password = "password", token = "TOKEN";
        TokenRequest tokenRequest = new TokenRequest(username, password);
        Member member = new Member(1L, username, password, "name", "010-0000-0000");

        given(memberDao.findByUsername(member.getUsername()))
                .willReturn(member);
        given(jwtTokenProvider.createToken("1")).willReturn(token);

        assertThat(memberService.createToken(tokenRequest).getAccessToken()).isEqualTo(token);
    }
}
