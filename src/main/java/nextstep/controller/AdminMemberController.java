package nextstep.controller;

import nextstep.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminMemberController {

    private final MemberService memberService;

    public AdminMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.deleteById(id));
    }

}
