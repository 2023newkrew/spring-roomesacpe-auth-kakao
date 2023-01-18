package nextstep.theme.repository;

import nextstep.theme.entity.ThemeEntity;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Long save(ThemeEntity themeEntity);

    Optional<ThemeEntity> findById(Long id);

    List<ThemeEntity> findAll();

    int deleteById(Long id);
}
