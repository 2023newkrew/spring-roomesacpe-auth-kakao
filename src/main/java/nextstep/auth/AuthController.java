package nextstep.auth;


import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.exception.ReservationException;
import nextstep.support.exception.RoomEscapeException;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/token")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest tokenRequest) {
        Member member;
        try {
            member = memberService.findByUsername(tokenRequest.getUsername());
        } catch (RoomEscapeException e) {
            throw new ReservationException(RoomEscapeExceptionCode.AUTHORIZATION_FAIL);
        }

        TokenResponse tokenResponse = authService.createToken(member, tokenRequest.getPassword());
        return ResponseEntity.ok(tokenResponse);
    }

}
