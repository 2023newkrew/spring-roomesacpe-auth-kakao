package nextstep.reservations.util.mapper;

import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.dto.theme.ThemeRequestDto;
import nextstep.reservations.dto.theme.ThemeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {
    public Theme requestDtoToTheme(ThemeRequestDto requestDto) {
        return Theme.builder()
                .name(requestDto.getName())
                .desc(requestDto.getDesc())
                .price(requestDto.getPrice())
                .build();
    }

    public ThemeResponseDto themeToThemeResponseDto(Theme theme) {
        return ThemeResponseDto.builder()
                .id(theme.getId())
                .name(theme.getName())
                .desc(theme.getDesc())
                .price(theme.getPrice())
                .build();
    }
}
