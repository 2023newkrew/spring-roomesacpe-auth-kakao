package nextstep.member;

import javax.servlet.http.HttpServletRequest;
import nextstep.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(HttpServletRequest request) {
        String accessToken = request.getHeader("authorization").split(" ")[1];
        Member member = memberService.findByUsername(authService.getPrincipal(accessToken));
        return ResponseEntity.ok(member);
    }
}
