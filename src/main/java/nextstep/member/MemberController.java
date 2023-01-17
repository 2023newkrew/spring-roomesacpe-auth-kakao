package nextstep.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private static final String ACCESS_TOKEN = "accessToken";

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(HttpServletRequest request) {
        Member member = memberService.findByToken(String.valueOf(request.getAttribute(ACCESS_TOKEN)));
        return ResponseEntity.ok(member);
    }
}
