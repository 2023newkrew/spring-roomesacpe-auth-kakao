package nextstep.interfaces.theme;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeListController {

    private ThemeService themeService;

    public ThemeListController(ThemeService themeService) {
        this.themeService = themeService;
    }
    @GetMapping
    public ResponseEntity<List<Theme>> showThemes() {
        List<Theme> results = themeService.findAll();
        return ResponseEntity.ok().body(results);
    }
}
