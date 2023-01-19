package nextstep.member.controller;

import java.net.URI;
import nextstep.auth.argumentresolver.Login;
import nextstep.member.dto.LoginMember;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
import nextstep.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        return ResponseEntity.created(URI.create("/members/" + memberService.create(memberRequest))).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@Login LoginMember loginMember) {
        return ResponseEntity.ok(MemberResponse.of(memberService.findById(loginMember.getId())));
    }
}

