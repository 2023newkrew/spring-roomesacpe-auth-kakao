package nextstep.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> loginWithToken(@RequestBody TokenRequest tokenRequest){
        TokenResponse token = authService.createToken(tokenRequest);
        return ResponseEntity.ok().body(token);
    }
}
