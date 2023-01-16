package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.dto.request.LoginMember;
import nextstep.auth.presentation.argumentresolver.AuthenticationPrincipal;
import nextstep.dto.response.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal LoginMember loginMember) {
        MemberResponse memberResponse = MemberResponseConverter.memberResponse(memberService.findById(loginMember.getId()));

        return ResponseEntity.ok(memberResponse);
    }
}
