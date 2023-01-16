package nextstep.member;

import javax.servlet.http.HttpServletRequest;
import nextstep.auth.AuthService;
import nextstep.auth.AuthenticationPrincipal;
import nextstep.auth.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;
    private AuthService authService;

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
    public ResponseEntity me() {
        Long id = 1L;
        Member member = memberService.findById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/you")
    public ResponseEntity<MemberResponse> findYourInfo(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        MemberResponse member = authService.findMemberByToken(token);
        return ResponseEntity.ok().body(member);
    }
}
