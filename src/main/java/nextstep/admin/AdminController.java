package nextstep.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nextstep.theme.ThemeRequest;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ThemeService themeService;

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id))
                .build();
    }
}
