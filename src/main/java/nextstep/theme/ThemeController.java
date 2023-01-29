package nextstep.theme;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ThemeController {
    private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/admin/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> showThemes() {
        List<Theme> results = themeService.findAll();
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/admin/themes/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        int deletedRowCount = themeService.delete(id);

        return ResponseEntity.ok().body(Collections.singletonMap("deletedThemeCount", deletedRowCount));
    }
}
