package nextstep.member;

import nextstep.support.LoginMember;
import nextstep.support.excpetion.InvalidAuthorizationTokenException;
import nextstep.support.excpetion.NotExistMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(@LoginMember Member member) {
        return ResponseEntity.ok(memberService.findByUserName(member.getUsername()));
    }

    @ExceptionHandler()
    public ResponseEntity handleBadRequestException(NotExistMemberException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler()
    public ResponseEntity handleUnauthorizedException(InvalidAuthorizationTokenException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
