package nextstep.theme;

import java.util.Objects;

public class Theme {
    private Long id;
    private final String name;
    private final String desc;
    private final int price;

    public static Theme giveId(Theme theme, Long id){
        theme.id = id;
        return theme;
    }
    public static ThemeBuilder builder() {
        return new ThemeBuilder();
    }

    public static class ThemeBuilder {

        private String name;

        private String desc;
        private Integer price;
        public ThemeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ThemeBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public ThemeBuilder price(int price) {
            this.price = price;
            return this;
        }

        public Theme build() {
            validate();
            return new Theme(name, desc, price);
        }

        private void validate() {
            if (Objects.isNull(desc)) {
                throw new IllegalArgumentException("desc는 널일 수 없습니다.");
            }

            if (Objects.isNull(name)) {
                throw new IllegalArgumentException("name은 널일 수 없습니다.");
            }

            if (Objects.isNull(price)) {
                throw new IllegalArgumentException("price는 널일 수 없습니다.");
            }

        }


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

    private Theme(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
