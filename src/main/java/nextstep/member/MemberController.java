package nextstep.member;

import nextstep.auth.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        Long id = memberService.create(memberCreateRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<Member> me(@AuthenticationPrincipal String principal) {
        Member member = memberService.findById(Long.parseLong(principal));
        return ResponseEntity.ok(member);
    }

    @PostMapping("/admin/authorization")
    public ResponseEntity<Member> addAdmin(@RequestBody MemberAuthorizationRequest memberAuthorizationRequest) {
        Member member = memberService.authorization(memberAuthorizationRequest);
        return ResponseEntity.ok(member);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
