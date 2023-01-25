package nextstep.theme.controller;

import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nextstep.theme.dto.request.ThemeRequest;
import nextstep.theme.service.ThemeAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/admin/themes")
@RequiredArgsConstructor
public class ThemeAdminController {

    private final ThemeAdminService themeAdminService;

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody @Valid ThemeRequest themeRequest) {
        Long id = themeAdminService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable @Min(1L) Long id) {
        themeAdminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
