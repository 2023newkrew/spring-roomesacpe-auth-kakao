package nextstep.member;

import nextstep.auth.AuthenticationPrincipal;
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
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest memberRequest) {
        Member member = memberService.create(memberRequest);
        MemberResponse res = new MemberResponse(member.getId(), member.getPassword(), member.getName(), member.getPhone());
        return ResponseEntity.created(URI.create("/members/").resolve(member.getId().toString())).body(res);
    }

    @GetMapping("/me")
    public MemberResponse me(@AuthenticationPrincipal Member member) {
        MemberResponse res = new MemberResponse(member.getId(), member.getUsername(), member.getName(), member.getPhone());
        return res;
    }
}
