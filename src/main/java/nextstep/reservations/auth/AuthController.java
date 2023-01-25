package nextstep.reservations.auth;

import nextstep.reservations.dto.auth.TokenRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody TokenRequestDto requestTokenDto) {
        return ResponseEntity.ok().body("Hello");
    }
}
