package nextstep.theme;

import nextstep.auth.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/admin/themes")
    public ResponseEntity<Void> createTheme(@LoginMember nextstep.member.LoginMember loginMember, @RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(loginMember, themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> showThemes() {
        List<Theme> results = themeService.findAll();
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/admin/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@LoginMember nextstep.member.LoginMember loginMember, @PathVariable Long id) {
        themeService.delete(loginMember, id);

        return ResponseEntity.noContent().build();
    }
}
