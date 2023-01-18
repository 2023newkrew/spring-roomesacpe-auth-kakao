package nextstep.admin;

import nextstep.member.MemberService;
import nextstep.member.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> upgradeRole(@PathVariable Long id) {
        return null;
    }

    // TODO : 관리자 권한, 경로 같은데 어떻게?
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> downgradeRole(@PathVariable Long id) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>>  getMembers() {
        return null;
    }
}
