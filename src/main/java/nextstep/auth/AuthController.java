package nextstep.auth;

import nextstep.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthController(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @PostMapping("/token")
    public ResponseEntity login(@RequestBody TokenRequest request) {
        memberService.validateIsMember(request);
        String accessToken = jwtTokenProvider.createToken(request.getUsername());
        return ResponseEntity.ok().body(new TokenResponse(accessToken));
    }
}
