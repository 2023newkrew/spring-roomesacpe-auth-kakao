package nextstep.interfaces.controller;

import nextstep.domain.domain.Member;
import nextstep.domain.model.request.MemberRequest;
import nextstep.domain.model.template.annotation.Login;
import nextstep.domain.model.template.annotation.NoAuth;
import nextstep.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

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

    @NoAuth
    @GetMapping
    public ResponseEntity<List<Member>> getMemberList() {
        return ResponseEntity.ok(memberService.findAll());
    }


    @GetMapping("/me")
    public ResponseEntity<Member> me(@Login Long memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }
}
