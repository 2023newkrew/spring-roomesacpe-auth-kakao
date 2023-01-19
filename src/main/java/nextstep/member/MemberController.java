package nextstep.member;

import nextstep.auth.LoginMember;
import nextstep.config.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import nextstep.auth.AuthService;
import nextstep.auth.AuthorizationExtractor;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(@AuthenticationPrincipal LoginMember loginMember) {
        Member member = memberService.findById(loginMember.getMemberId());
        return ResponseEntity.ok(member);
    }
}
