package nextstep.auth;


import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.exception.AuthenticationException;
import nextstep.support.exception.MemberException;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/token")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest tokenRequest) {
        Member member;

        try {
            member = memberService.findByUsernameAndPassword(tokenRequest.getUsername(), tokenRequest.getPassword());
        } catch (MemberException e) {
            throw new AuthenticationException(RoomEscapeExceptionCode.AUTHENTICATION_FAIL);
        }

        TokenResponse tokenResponse = authService.createToken(member);
        return ResponseEntity.ok(tokenResponse);
    }

}
