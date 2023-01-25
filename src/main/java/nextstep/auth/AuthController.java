package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        Member member = memberService.findByUsername(tokenRequest.getUsername());
        String accessToken = authService.createAccessToken(member, tokenRequest.getPassword());
        return ResponseEntity.ok(new TokenResponse(accessToken));
    }
}
