package nextstep.theme.repository;

import nextstep.theme.dao.ThemeDao;
import nextstep.theme.domain.Theme;
import nextstep.theme.mapper.ThemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {

    private final ThemeDao themeDao;

    @Autowired
    public ThemeRepositoryImpl(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @Override
    public Long save(Theme theme) {

        return themeDao.save(ThemeMapper.INSTANCE.domainToEntity(theme));
    }

    @Override
    public Optional<Theme> findById(Long id) {

        return Optional.ofNullable(ThemeMapper.INSTANCE.entityToDomain(themeDao.findById(id)));
    }

    @Override
    public List<Theme> findAll() {

        return ThemeMapper.INSTANCE.entityListToDomainList(themeDao.findAll());
    }

    @Override
    public int deleteById(Long id) {

        return themeDao.deleteById(id);
    }
}
