package nextstep.theme.datamapper;

import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.entity.ThemeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    ThemeResponse entityToDto(ThemeEntity themeEntity);
}
