package nextstep.theme;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<ThemeResponse> createTheme(@Valid @RequestBody ThemeRequest themeRequest) {
        Theme theme = themeService.create(themeRequest);
        ThemeResponse res = new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
        return ResponseEntity.created(URI.create("/themes/").resolve(theme.getId().toString())).body(res);
    }

    @GetMapping
    public List<ThemeResponse> showThemes() {
        List<Theme> themes = themeService.findAll();
        List<ThemeResponse> res = themes.stream()
                .map(t -> new ThemeResponse(t.getId(), t.getName(), t.getDesc(), t.getPrice()))
                .collect(Collectors.toList());
        return res;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheme(@PathVariable Long id) {
        themeService.delete(id);
    }
}
