package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private MemberService memberService;

    private JwtTokenProvider jwtTokenProvider;

    public AuthController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        Member member = memberService.findByUsername(tokenRequest.getUsername(), tokenRequest.getPassword());

        return ResponseEntity.ok(new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getUsername())));
    }
}
