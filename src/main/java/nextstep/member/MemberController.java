package nextstep.member;

import nextstep.auth.annotation.AuthenticatedMember;
import nextstep.auth.annotation.LoginRequired;
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
    @LoginRequired
    public ResponseEntity<MemberResponse> me(@AuthenticatedMember Member member) {
        return ResponseEntity.ok().body(MemberResponse.from(member));
    }

}
