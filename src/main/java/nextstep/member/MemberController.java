package nextstep.member;

import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(HttpServletRequest httpServletRequest) {
        String token = AuthorizationExtractor.extract(httpServletRequest);
        if (jwtTokenProvider.validateToken(token)) {
            Member member = memberService.findById(Long.parseLong(jwtTokenProvider.getPrincipal(token)));
            return ResponseEntity.ok(member);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
