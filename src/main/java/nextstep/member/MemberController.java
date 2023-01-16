package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import nextstep.support.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(@RequestHeader(value="Authorization") String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().build();
        }
        String username = jwtTokenProvider.getPrincipal(token);
        Member member = memberService.findByUsername(username);
        return ResponseEntity.ok(new MemberResponse(member));
    }
}
