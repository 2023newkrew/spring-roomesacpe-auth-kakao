package nextstep.reservations.controller.theme;

import nextstep.reservations.domain.service.theme.ThemeService;
import nextstep.reservations.dto.theme.ThemeRequestDto;
import nextstep.reservations.dto.theme.ThemeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(final ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Object> addTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        Long id = themeService.addTheme(themeRequestDto);
        return ResponseEntity
                .created(URI.create("/themes/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> getAllThemes() {
        List<ThemeResponseDto> allThemes = themeService.getAllThemes();
        return ResponseEntity
                .ok()
                .body(allThemes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
