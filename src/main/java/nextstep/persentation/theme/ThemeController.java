package nextstep.persentation.theme;

import nextstep.domain.theme.ThemeService;
import nextstep.dto.theme.ThemeRequest;
import nextstep.persistence.theme.Theme;
import nextstep.support.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/themes")
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
        return ResponseEntity.created(URI.create("/admin/themes/" + id)).build();
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
