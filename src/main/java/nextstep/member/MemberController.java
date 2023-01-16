package nextstep.member;

import lombok.AllArgsConstructor;
import nextstep.auth.AuthService;
import nextstep.infrastructure.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        String username = authService.findUsernameByToken(accessToken);
        MemberResponseDto memberDto = memberService.findByUsername(username);
        return ResponseEntity.ok(memberDto);
    }
}
