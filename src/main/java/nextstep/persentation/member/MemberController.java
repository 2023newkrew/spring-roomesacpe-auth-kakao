package nextstep.persentation.member;

import nextstep.annotation.AuthenticationPrincipal;
import nextstep.domain.member.MemberService;
import nextstep.dto.member.MemberRequest;
import nextstep.persistence.member.LoginMember;
import nextstep.persistence.member.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        if (memberRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(@AuthenticationPrincipal LoginMember loginMember) {
        Optional<Member> member = memberService.findById(loginMember.getId());
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
