package nextstep.auth.controller;

import nextstep.global.util.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.service.MemberService;
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
