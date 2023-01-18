package nextstep.theme;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private int price;
}
