package nextstep.auth.controller;

import lombok.RequiredArgsConstructor;
import nextstep.auth.service.AuthService;
import nextstep.auth.model.TokenRequest;
import nextstep.auth.model.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity tokenLogin(@Valid @RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.createToken(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
