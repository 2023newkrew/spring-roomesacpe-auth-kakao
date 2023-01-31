package nextstep.admin;

import nextstep.common.Authenticated;
import nextstep.auth.AuthService;
import nextstep.auth.dto.TokenResponse;
import nextstep.common.LoginMember;
import nextstep.common.Role;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.member.dto.MemberResponse;
import nextstep.theme.ThemeService;
import nextstep.theme.dto.ThemeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;
    private final ThemeService themeService;
    private final AuthService authService;

    public AdminController(MemberService memberService, ThemeService themeService, AuthService authService) {
        this.memberService = memberService;
        this.themeService = themeService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> registerAdmin(@Authenticated LoginMember loginMember) {
        Member member = memberService.findAndUpdateRole(loginMember.getId(), Role.ADMIN);
        return ResponseEntity.ok(authService.createToken(member));
    }

    @DeleteMapping
    public ResponseEntity<TokenResponse> deregisterAdmin(@Authenticated LoginMember loginMember) {
        Member member = memberService.findAndUpdateRole(loginMember.getId(), Role.MEMBER);
        return ResponseEntity.ok(authService.createToken(member));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> responses = memberService.findAll()
                .stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
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
