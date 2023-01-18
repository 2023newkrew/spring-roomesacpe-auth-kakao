package nextstep.member;

import nextstep.auth.AuthPrincipal;
import nextstep.auth.LoginRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createMember(@Valid @RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @LoginRequired
    @GetMapping("/me")
    public ResponseEntity me(@AuthPrincipal Member member) {
        return ResponseEntity.ok(member);
    }
}
