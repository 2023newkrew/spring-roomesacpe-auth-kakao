package nextstep.member;

import nextstep.auth.principal.MemberAuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static nextstep.support.Messages.CREATE_USER;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<String> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).body(CREATE_USER.getMessage() + memberRequest.getName());
    }

    @GetMapping("/me")
    public ResponseEntity<Member> findMemberOfMine(@MemberAuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(member);
    }
}
