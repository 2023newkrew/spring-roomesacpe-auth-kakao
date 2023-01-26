package nextstep.reservations.controller.member;

import nextstep.reservations.domain.entity.member.LoginMember;
import nextstep.reservations.domain.entity.member.Member;
import nextstep.reservations.domain.service.member.MemberService;
import nextstep.reservations.dto.member.MemberRequestDto;
import nextstep.reservations.util.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Object> addMember(@RequestBody MemberRequestDto memberRequestDto) {
        Long memberId = memberService.addMember(memberRequestDto);
        return ResponseEntity
                .created(URI.create("/members/" + memberId))
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> showMe(@AuthenticationPrincipal LoginMember loginMember) {
        Member member = memberService.findById(loginMember.getId());
        return ResponseEntity
                .ok()
                .body(member);
    }
}
