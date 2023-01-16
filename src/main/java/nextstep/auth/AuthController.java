package nextstep.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final AuthService authService;

    public AuthController(JwtTokenProvider tokenProvider, AuthService authService) {
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> tokenLogin(@RequestBody TokenRequest tokenRequest) {
        Long memberId = authService.validateAndGetMemberId(tokenRequest);
        String accessToken = tokenProvider.createToken(memberId.toString());
        return ResponseEntity.ok(new TokenResponse(accessToken));
    }
}
