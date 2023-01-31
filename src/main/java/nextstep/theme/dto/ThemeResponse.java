package nextstep.theme.dto;

import nextstep.theme.Theme;

public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private int price;

    private ThemeResponse(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse of (Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
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
