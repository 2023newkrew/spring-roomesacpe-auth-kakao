package nextstep.theme;

import lombok.RequiredArgsConstructor;
import nextstep.theme.dto.ThemeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        Long id = themeService.create(themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Theme>> showThemes() {
        List<Theme> results = themeService.findAll();
        return ResponseEntity.ok()
                .body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}
