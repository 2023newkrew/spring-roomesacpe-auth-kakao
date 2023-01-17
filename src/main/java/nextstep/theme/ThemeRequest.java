package nextstep.theme;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ThemeRequest {
    private String name;
    private String desc;
    private int price;

    public ThemeRequest(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toEntity() {
        return new Theme(
                this.name,
                this.desc,
                this.price
        );
    }
}
