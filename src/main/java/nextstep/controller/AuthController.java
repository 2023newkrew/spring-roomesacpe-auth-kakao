package nextstep.controller;

import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import nextstep.service.AuthService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity tokenLogin(@RequestBody TokenRequest tokenRequest) {
        String accessToken = authService.createToken(tokenRequest.getMemberId(), tokenRequest.getPassword());
        TokenResponse tokenResponse = new TokenResponse(accessToken);
        return ResponseEntity.ok().body(tokenResponse);
    }
}