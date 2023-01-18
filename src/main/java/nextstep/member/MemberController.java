package nextstep.member;

import nextstep.auth.LoginMember;
import nextstep.config.auth.LoginUser;
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
    public ResponseEntity me(@LoginUser LoginMember loginMember) {
        MemberMyInfoResponse member = MemberMyInfoResponse.fromEntity(memberService.findById(loginMember.getId()));
        return ResponseEntity.ok(member);
    }
}
