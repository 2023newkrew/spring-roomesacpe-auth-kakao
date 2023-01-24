package nextstep.theme.mapper;

import nextstep.theme.domain.Theme;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.entity.ThemeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default ThemeResponse domainToDto(Theme theme) {
        if (theme == null) {
            return null;
        }

        Long id = theme.getId();
        String name = theme.getName();
        String desc = theme.getDesc();
        int price = theme.getPrice();

        return new ThemeResponse(id, name, desc, price);
    }

    List<ThemeResponse> domainListToResponseDtoList(List<Theme> themes);

    ThemeEntity domainToEntity(Theme theme);

    Theme entityToDomain(ThemeEntity themeEntity);

    List<Theme> entityListToDomainList(List<ThemeEntity> themeEntities);
}
