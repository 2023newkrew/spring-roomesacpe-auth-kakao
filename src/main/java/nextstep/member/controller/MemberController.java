package nextstep.member.controller;

import nextstep.global.annotation.AuthenticationPrincipal;
import nextstep.global.util.JwtTokenProvider;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import nextstep.member.dto.MemberRequest;
import nextstep.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<URI> createMember(@RequestBody @Validated MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal String memberId) {
        MemberResponse memberResponse = memberService.findById(Long.parseLong(memberId));
        return ResponseEntity.ok(memberResponse);
    }
}
