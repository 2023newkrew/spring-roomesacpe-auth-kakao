package nextstep.theme.service;

import nextstep.support.NotExistEntityException;
import nextstep.theme.datamapper.ThemeMapper;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.entity.ThemeEntity;
import nextstep.theme.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long create(String name, String desc, int price) {

        return themeRepository.save(ThemeEntity.of(name, desc, price));
    }

    public List<ThemeResponse> findAll() {

        return themeRepository.findAll().stream()
                .map(ThemeMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList())
                ;
    }

    public void deleteById(Long id) {
        themeRepository.findById(id)
                .orElseThrow(NotExistEntityException::new)
        ;

        themeRepository.deleteById(id);
    }
}
