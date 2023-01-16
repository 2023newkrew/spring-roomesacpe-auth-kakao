package nextstep.member;

import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;
    private final JwtTokenProvider tokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider tokenProvider) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(HttpServletRequest httpServletRequest) {
        String token = AuthorizationExtractor.extract(httpServletRequest);
        Member member = memberService.findByUsername(tokenProvider.getPrincipal(token));
        return ResponseEntity.ok(member);
    }
}
