package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        Member foundMember = Member.of(memberService.findByUsername(tokenRequestDto.getUsername()));
        memberService.validatePassword(foundMember, tokenRequestDto);
        String token = authService.login(foundMember, tokenRequestDto);
        return ResponseEntity.ok()
                .body(new TokenResponseDto(token));
    }

}
