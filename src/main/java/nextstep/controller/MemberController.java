package nextstep.controller;

import nextstep.support.util.JwtTokenProvider;
import nextstep.dto.request.MemberRequest;
import nextstep.dto.response.MemberResponse;
import nextstep.domain.Member;
import nextstep.service.MemberService;
import nextstep.support.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity me(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(new MemberResponse(member));
    }
}
