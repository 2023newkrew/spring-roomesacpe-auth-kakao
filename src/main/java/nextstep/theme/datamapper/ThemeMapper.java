package nextstep.theme.datamapper;

import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.entity.ThemeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default ThemeResponse entityToDto(ThemeEntity themeEntity) {
        if (themeEntity == null) {
            return null;
        }

        Long id = themeEntity.getId();
        String name = themeEntity.getName();
        String desc = themeEntity.getDesc();
        int price = themeEntity.getPrice();

        return new ThemeResponse(id, name, desc, price);
    }
}
