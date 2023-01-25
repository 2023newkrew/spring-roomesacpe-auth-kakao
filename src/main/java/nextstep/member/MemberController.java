package nextstep.member;

import nextstep.auth.AuthenticationPrincipal;
import nextstep.exception.DuplicateEntityException;
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
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal Long memberId) {
        Member member = memberService.findById(memberId);
        MemberResponse memberResponse = new MemberResponse(
                member.getId(), member.getUsername(), member.getName(), member.getPhone()
        );
        return ResponseEntity.ok(memberResponse);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
