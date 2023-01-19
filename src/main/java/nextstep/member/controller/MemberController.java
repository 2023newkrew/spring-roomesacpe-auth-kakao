package nextstep.member.controller;

import java.net.URI;
import nextstep.auth.argumentresolver.Login;
import nextstep.auth.service.AuthService;
import nextstep.member.dto.LoginMember;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
import nextstep.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        return ResponseEntity.created(URI.create("/members/" + memberService.create(memberRequest))).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberResponse> me(@Login LoginMember loginMember, @PathVariable Long id) {
        authService.validateId(loginMember.getId(), id);
        return ResponseEntity.ok(MemberResponse.of(memberService.findById(loginMember.getId())));
    }
}

