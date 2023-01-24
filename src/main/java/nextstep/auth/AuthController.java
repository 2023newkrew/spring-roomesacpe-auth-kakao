package nextstep.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/member")
    public ResponseEntity<TokenResponse> loginMember(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(authService.createMemberToken(tokenRequest));
    }

    @PostMapping("/admin")
    public ResponseEntity<TokenResponse> loginAdmin(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(authService.createAdminToken(tokenRequest));
    }
}

