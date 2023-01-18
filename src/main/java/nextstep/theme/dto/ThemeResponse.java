package nextstep.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeResponse {

    private Long id;
    private String name;
    private String desc;
    private int price;
}
