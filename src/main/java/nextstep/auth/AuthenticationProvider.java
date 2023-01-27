package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.jwt.DecodedJwtToken;
import nextstep.member.MemberService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationProvider {

    private final MemberService memberService;

    public LoginMember authenticate(DecodedJwtToken token) {
        Long memberId = token.getPrincipal();
        return LoginMember.from(
                memberService.findById(memberId)
        );
    }
}
