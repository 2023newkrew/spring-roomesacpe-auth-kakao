package nextstep.theme;

public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private int price;

    public ThemeResponse() {
    }

    public ThemeResponse(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse of(Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }

}
