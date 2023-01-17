package nextstep.member.controller;

import nextstep.auth.AuthenticationPrincipal;
import nextstep.member.dto.MemberRequest;
import nextstep.member.entity.Member;
import nextstep.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Member> me(@AuthenticationPrincipal String principal) {
        Member member = memberService.findById(Long.parseLong(principal));
        return ResponseEntity.ok(member);
    }
}
