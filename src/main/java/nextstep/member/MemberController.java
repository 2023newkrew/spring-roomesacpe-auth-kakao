package nextstep.member;

import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistMemberException;
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
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(HttpServletRequest request) {
        String token = AuthorizationTokenExtractor.extract(request)
                .orElseThrow(InvalidAuthorizationTokenException::new);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException();
        }
        String userName = jwtTokenProvider.getPrincipal(token);
        Member member = memberService.findByUserName(userName);
        return ResponseEntity.ok(member);
    }

    @ExceptionHandler(value = {NotExistMemberException.class, InvalidAuthorizationTokenException.class})
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
