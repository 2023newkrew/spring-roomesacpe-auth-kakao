package nextstep.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private int price;

    public Theme(String name, String desc, int price) {
        this.id = null;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
