package nextstep.auth;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import nextstep.exceptions.exception.AuthorizationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private AuthService authService;


    @Test
    void 비밀번호가_일치하지_않으면_오류가_발생한다() {
        TokenRequest tokenRequest = new TokenRequest(2L, "invalidPassword");
        Member member = new Member(
                2L,
                "username",
                "password",
                "name",
                "010-0000-0000",
                "MEMBER"
        );
        given(memberService.findById(member.getId())).willReturn(member);
        assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    void 비밀번호가_일치하면_토큰이_반환된다() {
        TokenRequest tokenRequest = new TokenRequest(2L, "password");
        Member member = new Member(
                2L,
                "username",
                "password",
                "name",
                "010-0000-0000",
                "MEMBER"
        );
        String token = "token";
        given(memberService.findById(member.getId())).willReturn(member);
        given(jwtTokenProvider.createToken(String.valueOf(member.getId()))).willReturn(token);
        assertThat(authService.createToken(tokenRequest).getAccessToken()).isEqualTo(token);
    }

}
