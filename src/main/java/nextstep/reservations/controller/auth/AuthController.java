package nextstep.reservations.controller.auth;

import nextstep.reservations.domain.service.auth.AuthService;
import nextstep.reservations.dto.auth.TokenReponseDto;
import nextstep.reservations.dto.auth.TokenRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenReponseDto> login(@RequestBody TokenRequestDto requestTokenDto) {
        TokenReponseDto accessToken = authService.createAccessToken(requestTokenDto);
        System.out.println(accessToken.toString());
        return ResponseEntity
                .ok()
                .body(accessToken);
    }
}
