package nextstep.auth;

import nextstep.exception.NotCorrectPasswordException;
import nextstep.exception.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(authService.createToken(tokenRequest));
    }

    @ExceptionHandler(value = {NotExistEntityException.class, NotCorrectPasswordException.class})
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
