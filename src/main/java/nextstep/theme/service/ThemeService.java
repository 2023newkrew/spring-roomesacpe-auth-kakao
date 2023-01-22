package nextstep.theme.service;

import nextstep.support.NotExistEntityException;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long create(String name, String desc, int price) {

        return themeRepository.save(Theme.of(name, desc, price));
    }

    public List<Theme> findAll() {

        return themeRepository.findAll();
    }

    public void deleteById(Long id) {
        if (themeRepository.deleteById(id) == 0) {
            throw new NotExistEntityException();
        }
    }
}
