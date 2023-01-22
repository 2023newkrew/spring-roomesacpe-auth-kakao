package nextstep.member;

import nextstep.auth.AuthenticatedUsername;
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
    public ResponseEntity<Object> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest.toEntity());
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthenticatedUsername String username) {
        Member member = memberService.findByUsername(username);
        return ResponseEntity.ok().body(MemberResponse.from(member));
    }

}
