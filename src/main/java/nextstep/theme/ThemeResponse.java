package nextstep.theme;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ThemeResponse {

    private final Long id;
    private final String name;
    private final String desc;
    private final int price;

    @JsonCreator
    public ThemeResponse(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }

    public Long getId() {
        return id;
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
}
