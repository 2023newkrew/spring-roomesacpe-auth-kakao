package nextstep.member;

import javax.swing.JWindow;
import nextstep.auth.JwtTokenProvider;
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
    public ResponseEntity<MemberResponse> me(@RequestHeader("Authorization") String token) {
        String tokenSplit = token.substring("Bearer ".length());

        if (!jwtTokenProvider.validateToken(tokenSplit)) {
            return ResponseEntity.badRequest().build();
        }

        Long id = Long.valueOf(jwtTokenProvider.getPrincipal(tokenSplit));
        Member member = memberService.findById(id);
        MemberResponse memberResponse = new MemberResponse(
                member.getId(), member.getUsername(), member.getName(), member.getPhone()
        );
        return ResponseEntity.ok(memberResponse);
    }
}
