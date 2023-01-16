package nextstep.auth;

import nextstep.support.NotCorrectPasswordException;
import nextstep.support.NotExistEntityException;
import nextstep.support.NotExistMemberException;
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

    @ExceptionHandler(value = {NotExistMemberException.class, NotCorrectPasswordException.class})
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
