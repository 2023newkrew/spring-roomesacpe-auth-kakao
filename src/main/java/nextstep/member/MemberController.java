package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.support.annotation.AuthorizationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<LoginMemberResponse> me(@AuthorizationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(LoginMemberResponse.fromEntity(loginMember));
    }
}
