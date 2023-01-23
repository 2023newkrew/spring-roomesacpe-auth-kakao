package nextstep.theme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> showThemes() {
        List<Theme> themes = themeService.findAll();
        List<ThemeResponse> themeResponses = themes.stream().map(ThemeResponse::fromEntity).collect(Collectors.toList());

        return ResponseEntity.ok().body(themeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
