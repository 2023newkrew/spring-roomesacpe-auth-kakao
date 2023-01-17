package nextstep.member;

import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.exception.InvalidAuthorizationTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
        URI uri = UriComponentsBuilder.fromUriString("/members/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(HttpServletRequest request) {
        String token = AuthorizationTokenExtractor.extract(request)
                .orElseThrow(() -> new InvalidAuthorizationTokenException("유효하지 않은 토큰입니다"));
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException("유효하지 않은 토큰입니다 - " + token);
        }
        String userName = jwtTokenProvider.getPrincipal(token);
        Member member = memberService.findByUserName(userName);
        return ResponseEntity.ok(member);
    }
}
