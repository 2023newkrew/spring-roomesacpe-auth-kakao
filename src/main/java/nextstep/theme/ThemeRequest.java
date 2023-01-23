package nextstep.theme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ThemeRequest {
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme toEntity() {
        return Theme.builder()
                .name(this.name)
                .desc(this.desc)
                .price(this.price)
                .build();
    }
}
