package nextstep.reservations.domain.entity.theme;

public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(final Long id, final String name, final String desc, final Integer price) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public static ThemeBuilder builder() {return new ThemeBuilder();}

    public static class ThemeBuilder {
        private Long id;
        private String name;
        private String desc;
        private Integer price;

        ThemeBuilder() {

        }

        public ThemeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ThemeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ThemeBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public ThemeBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public Theme build() {
            return new Theme(id, name, desc, price);
        }
    }
}
