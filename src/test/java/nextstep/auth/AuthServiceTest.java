package nextstep.auth;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

import nextstep.auth.dto.LoginRequest;
import nextstep.auth.dto.TokenRequest;
import nextstep.exceptions.exception.auth.AuthorizationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private AuthService authService;

    private static MockedStatic<TokenRequest> mTokenRequest;

    @BeforeAll
    public static void beforeClass() {
        mTokenRequest = mockStatic(TokenRequest.class);
    }

    @AfterAll
    public static void afterClass() {
        mTokenRequest.close();
    }

    @Test
    void 비밀번호가_일치하지_않으면_오류가_발생한다() {
        LoginRequest loginRequest = new LoginRequest(2L, "invalidPassword");
        Member member = new Member(
                2L,
                "username",
                "password",
                "name",
                "010-0000-0000",
                "MEMBER"
        );
        given(memberService.findById(member.getId())).willReturn(member);
        assertThatThrownBy(() -> authService.createToken(loginRequest))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    void 비밀번호가_일치하면_토큰이_반환된다() {
        LoginRequest loginRequest = new LoginRequest(2L, "password");
        Member member = new Member(
                2L,
                "username",
                "password",
                "name",
                "010-0000-0000",
                "MEMBER"
        );
        String token = "token";

        TokenRequest tokenRequest = new TokenRequest(member);
        given(memberService.findById(member.getId())).willReturn(member);
        given(TokenRequest.fromMember(member)).willReturn(tokenRequest);
        given(jwtTokenProvider.createToken(tokenRequest)).willReturn(token);
        assertThat(authService.createToken(loginRequest).getAccessToken()).isEqualTo(token);
    }

}
