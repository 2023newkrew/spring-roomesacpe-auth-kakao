package nextstep.theme.presentation;

import nextstep.support.NotExistEntityException;
import nextstep.theme.domain.Theme;
import nextstep.theme.domain.ThemeService;
import nextstep.theme.dto.ThemeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        if (themeRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Theme>> showThemes() {
        List<Theme> results = themeService.findAll();
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        try {
            themeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(NotExistEntityException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
