package nextstep.theme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeEntity {

    private Long id;
    private String name;
    private String desc;
    private int price;
}