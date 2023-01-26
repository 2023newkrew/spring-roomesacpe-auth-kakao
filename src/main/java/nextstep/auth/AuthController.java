package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.AuthorizationException;
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
        Member member = memberService.findByUsername(request.getUsername());
        if (member.checkWrongPassword(request.getPassword())) {
            throw new AuthorizationException("비밀번호가 올바르지 않습니다.");
        }
        String accessToken = jwtTokenProvider.createToken(request.getUsername());
        return ResponseEntity.ok().body(new TokenResponse(accessToken));
    }
}
