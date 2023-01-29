package nextstep.member;

import nextstep.auth.AuthPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<Member> me(@AuthPrincipal Member member) {
        return ResponseEntity.ok(member);
    }

    @PostMapping("/admin/members/admin")
    public ResponseEntity<Void> makeUserToAdmin(@RequestBody MemberRequest usernameRequest) {
        memberService.updateUserToAdmin(usernameRequest.getUsername());
        return ResponseEntity.noContent().build();
    }
}
