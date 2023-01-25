package nextstep.auth;


import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.exception.AuthorizationExcpetion;
import nextstep.support.exception.MemberException;
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
            member = memberService.findByUsernameAndPassword(tokenRequest.getUsername(), tokenRequest.getPassword());
        } catch (MemberException e) {
            throw new AuthorizationExcpetion(RoomEscapeExceptionCode.AUTHORIZATION_FAIL);
        }

        TokenResponse tokenResponse = authService.createToken(member);
        return ResponseEntity.ok(tokenResponse);
    }

}
