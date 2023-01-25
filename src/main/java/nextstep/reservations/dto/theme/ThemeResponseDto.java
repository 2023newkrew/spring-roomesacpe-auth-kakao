package nextstep.reservations.dto.theme;

public class ThemeResponseDto {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeResponseDto(final Long id, final String name, final String desc, final Integer price) {
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

    public static ThemeResponseDtoBuilder builder() {return new ThemeResponseDtoBuilder();}

    public static class ThemeResponseDtoBuilder {
        private Long id;
        private String name;
        private String desc;
        private Integer price;

        public ThemeResponseDtoBuilder() {
        }

        public ThemeResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ThemeResponseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ThemeResponseDtoBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public ThemeResponseDtoBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public ThemeResponseDto build() {
            return new ThemeResponseDto(id, name, desc, price);
        }
    }
}
