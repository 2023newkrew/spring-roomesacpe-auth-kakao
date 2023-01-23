package nextstep.theme;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Theme {
    private final Long id;
    @NonNull
    private final String name;
    @NonNull
    private final String desc;
    @NonNull
    private final Integer price;
}
