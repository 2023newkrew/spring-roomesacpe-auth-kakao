package nextstep.auth.controller;

import nextstep.auth.dto.AuthRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest authRequest) {
        TokenResponse tokenResponse = authService.login(authRequest.getUsername(), authRequest.getPassword());

        return ResponseEntity.ok(tokenResponse);
    }
}
