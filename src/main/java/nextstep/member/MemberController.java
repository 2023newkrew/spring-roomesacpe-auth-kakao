package nextstep.member;

import nextstep.auth.JwtTokenProvider;
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
    public ResponseEntity me(HttpServletRequest httpServletRequest) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = httpServletRequest.getHeader("authorization").substring("Bearer ".length());
        String username = jwtTokenProvider.getPrincipal(token);
        Member member = memberService.findByUsername(username);
        return ResponseEntity.ok(member);
    }
}
