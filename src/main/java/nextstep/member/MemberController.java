package nextstep.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final String TOKEN_KEY = "authorization";

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/me")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(
            HttpServletRequest httpServletRequest
    ) {
        String token = httpServletRequest.getHeader(TOKEN_KEY);
        Member member = memberService.findByToken(token);
        return ResponseEntity.ok(member);
    }
}
