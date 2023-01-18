package nextstep.theme.repository;

import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.ThemeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public Long save(ThemeEntity themeEntity) {

        return themeDao.save(themeEntity);
    }

    @Override
    public Optional<ThemeEntity> findById(Long id) {

        return Optional.ofNullable(themeDao.findById(id));
    }

    @Override
    public List<ThemeEntity> findAll() {

        return Optional.ofNullable(themeDao.findAll())
                .orElse(new ArrayList<>())
                ;
    }

    @Override
    public int deleteById(Long id) {

        return themeDao.deleteById(id);
    }
}
