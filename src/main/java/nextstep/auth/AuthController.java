package nextstep.auth;

import nextstep.support.excpetion.NotCorrectPasswordException;
import nextstep.support.excpetion.NotExistMemberException;
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
    public ResponseEntity handle(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }
}
