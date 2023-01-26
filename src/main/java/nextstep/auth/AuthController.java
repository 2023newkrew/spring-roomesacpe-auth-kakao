package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.auth.dto.TokenResponseDto;
import nextstep.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        authService.validateUsernameAndPassword(tokenRequestDto);
        String token = authService.login(tokenRequestDto);
        return ResponseEntity.ok()
            .body(new TokenResponseDto(token));
    }

}
