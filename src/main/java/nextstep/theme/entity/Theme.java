package nextstep.theme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    private Long id;
    private String name;
    private String desc;
    private int price;
}
