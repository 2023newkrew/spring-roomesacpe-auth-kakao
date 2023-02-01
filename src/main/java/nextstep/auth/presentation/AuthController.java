package nextstep.auth.presentation;

import nextstep.auth.domain.AuthService;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberService;
import nextstep.support.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final MemberService memberService;

    private final AuthService authService;

    public AuthController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        if (tokenRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Member member = memberService.findByUsername(tokenRequest.getUsername());
            String accessToken = authService.createAccessToken(member, tokenRequest.getPassword());
            return ResponseEntity.ok(new TokenResponse(accessToken));
        }
        catch(NotExistEntityException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
