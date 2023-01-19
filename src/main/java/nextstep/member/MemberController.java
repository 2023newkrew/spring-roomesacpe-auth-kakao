package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<URI> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        MemberResponse response = MemberResponse.of(memberService.findByToken(token));
        return ResponseEntity.ok(response);
    }
}
