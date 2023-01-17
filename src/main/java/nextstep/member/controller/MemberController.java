package nextstep.member.controller;

import nextstep.global.config.annotation.ExtractPrincipal;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
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
        Long id = memberService.create(memberRequest.getUsername(), memberRequest.getPassword(),
                memberRequest.getName(), memberRequest.getPhone());

        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@ExtractPrincipal String memberId) {
        MemberResponse memberResponse = memberService.findById(Long.parseLong(memberId));

        return ResponseEntity.ok(memberResponse);
    }
}
