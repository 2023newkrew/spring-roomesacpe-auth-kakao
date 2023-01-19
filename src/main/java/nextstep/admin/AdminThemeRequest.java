package nextstep.admin;

import nextstep.theme.Theme;

public class AdminThemeRequest {

    private String name;
    private String desc;
    private int price;

    private AdminThemeRequest() {
    }

    public AdminThemeRequest(String name, String desc, int price) {
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
