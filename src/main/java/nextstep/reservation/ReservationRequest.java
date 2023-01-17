package nextstep.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {
    private final Long scheduleId;
    private final String name;
}
