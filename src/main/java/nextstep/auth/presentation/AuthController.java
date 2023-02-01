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

import java.util.Optional;

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
        Optional<Member> member = memberService.findByUsername(tokenRequest.getUsername());
        if (member.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<String> accessToken = authService.createAccessToken(member.get(), tokenRequest.getPassword());
        return accessToken.map(s -> ResponseEntity.ok(new TokenResponse(s))).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
