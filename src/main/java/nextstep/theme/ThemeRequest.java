package nextstep.theme;

import javax.validation.constraints.NotNull;

public class ThemeRequest {
    @NotNull
    private String name;
    @NotNull
    private String desc;
    @NotNull
    private int price;

    public ThemeRequest() {}

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
