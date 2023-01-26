package nextstep.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.theme.Theme;

@Getter
@AllArgsConstructor
public final class ThemeRequestDto {

    private String name;
    private String desc;
    private int price;

    public Theme toEntity() {
        return new Theme(
            this.name,
            this.desc,
            this.price
        );
    }
}
