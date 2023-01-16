package nextstep.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<LoginMember> me(@AuthenticationPrincipal LoginMember loginMember) {
//        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
//        String token = httpServletRequest.getHeader("authorization").substring("Bearer ".length());
//        String username = jwtTokenProvider.getPrincipal(token);
//        Member member = memberService.findByUsername();
        return ResponseEntity.ok(loginMember);
    }
}
