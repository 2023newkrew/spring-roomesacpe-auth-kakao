package nextstep.member.controller;

import nextstep.global.annotation.AuthenticationPrincipal;
import nextstep.global.util.JwtTokenProvider;
import nextstep.member.entity.Member;
import nextstep.member.dto.MemberRequest;
import nextstep.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;
    private final JwtTokenProvider tokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider tokenProvider) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(@AuthenticationPrincipal String username) {
        Member member = memberService.findByUsername(username);
        return ResponseEntity.ok(member);
    }
}
