package nextstep.member;

import nextstep.auth.AuthorizationTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.LoginMember;
import nextstep.support.excpetion.InvalidAuthorizationTokenException;
import nextstep.support.excpetion.NotExistMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(@LoginMember Member member) {
        return ResponseEntity.ok().body(member);
    }

    @ExceptionHandler()
    public ResponseEntity handleBadRequestException(NotExistMemberException ex) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler()
    public ResponseEntity handleUnauthorizedException(InvalidAuthorizationTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
