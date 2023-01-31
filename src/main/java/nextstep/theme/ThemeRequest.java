package nextstep.theme;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ThemeRequest {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Length(max = 20, message = "이름은 20자 이하여야 합니다.")
    private final String name;

    @NotBlank(message = "설명은 공백일 수 없습니다.")
    @Length(max = 255, message = "설명은 255자 이하여야 합니다.")
    private final String desc;

    @Min(value = 0, message = "가격은 0원 이상 100,000원 이하여야 합니다.")
    @Max(value = 100_000, message = "가격은 0원 이상 100,000원 이하여야 합니다.")
    private final int price;

    public ThemeRequest(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }

    public Theme toEntity() {
        return new Theme(
                this.name,
                this.desc,
                this.price
        );
    }
}
