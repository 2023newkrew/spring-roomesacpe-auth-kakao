package nextstep.theme;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> showThemes() {
        List<Theme> themes = themeService.findAll();
        List<ThemeResponse> themeResponses = themes.stream().map(ThemeResponse::fromEntity).collect(Collectors.toList());

        return ResponseEntity.ok().body(themeResponses);
    }
}
