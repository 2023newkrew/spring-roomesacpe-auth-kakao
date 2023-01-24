package nextstep.theme.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Theme {

    private Long id;
    private String name;
    private String desc;
    private int price;

    public static Theme of(String name, String desc, int price) {

        return new Theme(null, name, desc, price);
    }
}
