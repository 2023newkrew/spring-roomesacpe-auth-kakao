package nextstep.theme.dto;

import nextstep.theme.domain.Theme;

import java.util.Arrays;

public class ThemeRequest {
    private String name;
    private String desc;
    private int price;

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public ThemeRequest() {
    }

    public ThemeRequest(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getDesc() {
        return desc;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
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

    public boolean validate() {
        return price >= 0 && isNullOrEmptyOrBlank(name, desc);
    }

    private boolean isNullOrEmptyOrBlank(String... values) {
        return Arrays.stream(values)
                .anyMatch(value -> value == null || value.isEmpty() || value.isBlank());
    }
}
