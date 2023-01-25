package nextstep.reservations.dto.theme;

public class ThemeRequestDto {
    private String name;
    private String desc;
    private Integer price;

    /* requestbody에서 사용 */
    @SuppressWarnings("unused")
    public ThemeRequestDto() {}

    public ThemeRequestDto(final String name, final String desc, final Integer price) {
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

    public Integer getPrice() {
        return price;
    }

    public ThemeRequestDtoBuilder builder() {return new ThemeRequestDtoBuilder();}

    public static class ThemeRequestDtoBuilder {
        private String name;
        private String desc;
        private Integer price;

        public ThemeRequestDtoBuilder() {
        }

        public ThemeRequestDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ThemeRequestDtoBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public ThemeRequestDtoBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public ThemeRequestDto build() {
            return new ThemeRequestDto(name, desc, price);
        }
     }
}
