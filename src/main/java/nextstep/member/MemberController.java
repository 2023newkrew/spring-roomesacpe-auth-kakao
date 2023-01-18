package nextstep.member;

import nextstep.auth.AuthenticationPrincipal;
import nextstep.auth.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Member member = memberService.findByToken(token);
        MemberResponse res = new MemberResponse(member.getId(), member.getUsername(), member.getPassword(), member.getName(), member.getPhone());
        return ResponseEntity.ok(res);
    }
}
