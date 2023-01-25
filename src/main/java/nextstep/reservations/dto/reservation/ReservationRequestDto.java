package nextstep.reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    LocalTime time;

    String name;

    String themeName;

    String themeDesc;

    Integer themePrice;

    /* requestBody에서 사용 */
    @SuppressWarnings("unused")
    public ReservationRequestDto() {}

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getThemeName() {
        return themeName;
    }

    public String getThemeDesc() {
        return themeDesc;
    }

    public Integer getThemePrice() {
        return themePrice;
    }
}
