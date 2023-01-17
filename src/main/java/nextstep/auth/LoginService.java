package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.LoginException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberService.findByUsername(tokenRequest.getUsername());

        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new LoginException();
        }

        return new TokenResponse(jwtTokenProvider.createToken(member.getId().toString()));
    }
}
