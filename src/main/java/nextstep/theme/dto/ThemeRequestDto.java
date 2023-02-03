package nextstep.theme.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.theme.Theme;

@Getter
@AllArgsConstructor
public final class ThemeRequestDto {

    @NotBlank(message = "테마이름은 빈칸일 수 없습니다.")
    private String name;
    @NotBlank(message = "테마설명은 빈칸일 수 없습니다.")
    private String desc;
    @Positive(message = "가격은 음수가 될 수 없습니다.")
    private int price;

    public Theme toEntity() {
        return new Theme(
            this.name,
            this.desc,
            this.price
        );
    }
}
