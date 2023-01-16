package nextstep.auth;

import nextstep.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final MemberService memberService;

    public AuthController(JwtTokenProvider tokenProvider, MemberService memberService) {
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> tokenLogin(@RequestBody TokenRequest tokenRequest) {
        memberService.validateUserPassword(tokenRequest);
        String accessToken = tokenProvider.createToken(tokenRequest.getUsername());
        return ResponseEntity.ok(new TokenResponse(accessToken));
    }
}
