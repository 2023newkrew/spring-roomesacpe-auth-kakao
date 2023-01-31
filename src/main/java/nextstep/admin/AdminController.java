package nextstep.admin;

import nextstep.common.Authenticated;
import nextstep.common.LoginMember;
import nextstep.member.MemberService;
import nextstep.member.dto.MemberResponse;
import nextstep.theme.ThemeService;
import nextstep.theme.dto.ThemeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final ThemeService themeService;

    public AdminController(MemberService memberService, ThemeService themeService) {
        this.memberService = memberService;
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> registerAdmin(@Authenticated LoginMember loginMember) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deregisterAdmin(@Authenticated LoginMember loginMember) {
        return null;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>>  getMembers() {
        return null;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest.toEntity());
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
