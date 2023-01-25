package nextstep.theme;

import nextstep.support.InvalidInputException;

public class ThemeRequest {
    private final String name;
    private final String desc;
    private final int price;

    public ThemeRequest(String name, String desc, int price) {
        validatePrice(price);
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    private static void validatePrice(int price) {
        if (price < 0) {
            throw new InvalidInputException();
        }
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
