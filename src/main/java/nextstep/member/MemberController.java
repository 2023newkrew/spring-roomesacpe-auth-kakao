package nextstep.member;

import nextstep.common.Authenticated;
import nextstep.common.LoginMember;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberCreatedResponse;
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
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberCreatedResponse> me(@Authenticated LoginMember loginMember) {
        Member member = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(MemberCreatedResponse.of(member));
    }
}
