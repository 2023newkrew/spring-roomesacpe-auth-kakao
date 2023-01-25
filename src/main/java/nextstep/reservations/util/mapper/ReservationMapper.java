package nextstep.reservations.util.mapper;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public Reservation requestDtoToReservation(ReservationRequestDto requestDto) {
        return Reservation.builder()
                .date(requestDto.getDate())
                .time(requestDto.getTime())
                .name(requestDto.getName())
                .theme(Theme.builder()
                        .name(requestDto.getThemeName())
                        .desc(requestDto.getThemeDesc())
                        .price(requestDto.getThemePrice())
                        .build())
                .build();
    }

    public ReservationResponseDto reservationToResponseDto(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .name(reservation.getName())
                .themeName(reservation.getTheme().getName())
                .themeDesc(reservation.getTheme().getDesc())
                .themePrice(reservation.getTheme().getPrice())
                .build();
    }
}
