package nextstep.interfaces.controller;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.request.TokenRequest;
import nextstep.domain.model.response.TokenResponse;
import nextstep.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.createToken(tokenRequest));
    }
}