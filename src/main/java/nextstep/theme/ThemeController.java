package nextstep.theme;

import nextstep.support.excpetion.InvalidAuthorizationTokenException;
import nextstep.support.excpetion.NotExistReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
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
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity handleUnathorizedException(InvalidAuthorizationTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler()
    public ResponseEntity handleNotFoundException(NotExistReservationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
