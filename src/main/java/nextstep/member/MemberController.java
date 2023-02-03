package nextstep.member;

import nextstep.auth.domain.LoginMember;
import nextstep.auth.principal.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Void> createMember(@Validated @RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id))
                .build();
    }

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal LoginMember member) {
        return "hello";
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(@AuthenticationPrincipal LoginMember member) {
        return ResponseEntity.ok(
                memberService.findById(member.getId()));
    }
}
