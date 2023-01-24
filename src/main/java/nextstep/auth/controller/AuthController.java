package nextstep.auth.controller;

import lombok.RequiredArgsConstructor;
import nextstep.auth.service.AuthService;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> tokenLogin(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.login(tokenRequest.getUsername(), tokenRequest.getPassword());

        return ResponseEntity.ok(tokenResponse);
    }
}
