package nextstep.member.controller;

import java.util.List;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.member.service.MemberAdminService;
import nextstep.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {
    private final MemberAdminService memberAdminService;

    @GetMapping
    public ResponseEntity<List<Member>> showAllMembers() {
        List<Member> members = memberAdminService.findAll();
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") @Min(1L) Long id) {
        memberAdminService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
