/**
 * 인증 이랑 컨트롤러 분리 정확하게 어떤 말인지 모르겠긴 한데..
 * 학습테스트 복붙해서 토큰 생성되는지만 확인해볼게요!!
 */

package nextstep.auth.controller;

import nextstep.auth.service.AuthService;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
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
        TokenResponse tokenResponse = authService.createToken(tokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}
