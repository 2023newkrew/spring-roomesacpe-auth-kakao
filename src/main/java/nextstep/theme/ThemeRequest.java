package nextstep.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeRequest {

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
