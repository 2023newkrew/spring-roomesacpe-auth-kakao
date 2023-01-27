package nextstep.member;

import nextstep.auth.AuthorizationExtractor;
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
        Long id = memberService.create(memberRequest.getUsername(), memberRequest.getPassword(),
                memberRequest.getName(), memberRequest.getPhone());
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        MemberResponse response = MemberResponse.of(memberService.findByToken(token));
        return ResponseEntity.ok(response);
    }
}
