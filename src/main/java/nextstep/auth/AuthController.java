package nextstep.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static nextstep.auth.JwtTokenProvider.ACCESS_TOKEN;

@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login/token")
    public ResponseEntity<Void> createToken(@RequestBody @Valid TokenRequest tokenRequest, HttpServletResponse response) {
        String token = authService.createToken(tokenRequest);
        setAccessTokenInCookie(response, token);
        return ResponseEntity.noContent().build();
    }

    private void setAccessTokenInCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (jwtTokenProvider.getValidityInMilliseconds() / 1000));
        response.addCookie(cookie);
    }
}
