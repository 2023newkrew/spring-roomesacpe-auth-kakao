package nextstep.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public ThemeRequest(Theme theme) {
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }
}
