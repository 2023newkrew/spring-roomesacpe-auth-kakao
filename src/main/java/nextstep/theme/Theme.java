package nextstep.theme;

import org.h2.util.StringUtils;

import static nextstep.config.Messages.EMPTY_VALUE;

public class Theme {
    private Long id;
    private String name;
    private String desc;
    private int price;

    public Theme() {
    }

    public Theme(Long id, String name, String desc, int price) {
        checkEmptyValue(name, price);
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(String name, String desc, int price) {
        checkEmptyValue(name, price);
        this.name = name;
        this.desc = desc;
        this.price = price;
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

    private void checkEmptyValue(String name, int price){
        if (StringUtils.isNullOrEmpty(name)  || price == 0) {
            throw new NullPointerException(EMPTY_VALUE.getMessage());
        }
    }
}
