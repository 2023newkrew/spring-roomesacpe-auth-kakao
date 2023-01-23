package nextstep.reservation;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReservationRequest {
    private final Long scheduleId;
    private final String name;
}
