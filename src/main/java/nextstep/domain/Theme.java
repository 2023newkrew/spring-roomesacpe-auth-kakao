package nextstep.domain;

import nextstep.dto.request.ThemeRequest;

public class Theme {
    private Long id;
    private String name;
    private String desc;
    private int price;

    public Theme() {
    }

    public Theme(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(ThemeRequest themeRequest) {
        this.name = themeRequest.getName();
        this.desc = themeRequest.getDesc();
        this.price = themeRequest.getPrice();
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
