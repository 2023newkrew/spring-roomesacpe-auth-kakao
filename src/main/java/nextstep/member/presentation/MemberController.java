package nextstep.member.presentation;

import nextstep.annotation.AuthenticationPrincipal;
import nextstep.member.domain.LoginMember;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberService;
import nextstep.member.dto.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Object> createMember(@RequestBody MemberRequest memberRequest) {
        if (memberRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Object> me(@AuthenticationPrincipal LoginMember loginMember) {
        Member member = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(member);
    }
}
