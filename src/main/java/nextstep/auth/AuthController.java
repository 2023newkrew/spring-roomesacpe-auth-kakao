package nextstep.auth;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/token")
public class AuthController {
    JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getUsername())));
    }
}
