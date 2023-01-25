package nextstep.reservations.domain.service.theme;

import nextstep.reservations.dto.theme.ThemeRequestDto;
import nextstep.reservations.dto.theme.ThemeResponseDto;
import nextstep.reservations.exceptions.theme.exception.DuplicateThemeException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import nextstep.reservations.repository.theme.ThemeRepository;
import nextstep.reservations.util.mapper.ThemeMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    public ThemeService(final ThemeRepository themeRepository, final ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    public Long addTheme(final ThemeRequestDto themeRequestDto) {
        try {
            return themeRepository.add(themeMapper.requestDtoToTheme(themeRequestDto));
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateThemeException();
        }
    }

    public List<ThemeResponseDto> getAllThemes() {
        return themeRepository.findAll()
                .stream()
                .map(themeMapper::themeToThemeResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteTheme(final Long id) {
        int removeCount = themeRepository.remove(id);
        if (removeCount == 0) throw new NoSuchThemeException();
    }
}
