package nextstep.auth;

import nextstep.support.UnAuthorizedException;
import nextstep.support.ForbiddenAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> produceToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.createToken(tokenRequest));
    }
}
