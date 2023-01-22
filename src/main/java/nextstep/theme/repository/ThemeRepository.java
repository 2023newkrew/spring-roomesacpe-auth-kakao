package nextstep.theme.repository;

import nextstep.theme.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Long save(Theme themeEntity);

    Optional<Theme> findById(Long id);

    List<Theme> findAll();

    int deleteById(Long id);
}
