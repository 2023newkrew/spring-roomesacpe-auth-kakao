package nextstep.publics.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.createToken(request));
    }

    @PostMapping("/token-cookie")
    public ResponseEntity<Void> createTokenWithCookie(@RequestBody TokenRequest request) {
        String token = authService.createToken(request).getAccessToken();
        ResponseCookie responseCookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
