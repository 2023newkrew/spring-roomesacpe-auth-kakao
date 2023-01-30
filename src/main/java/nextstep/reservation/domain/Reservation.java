package nextstep.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {

    private Long id;
    private Long scheduleId;
    private Long memberId;

    public static Reservation of(Long scheduleId, Long memberId) {

        return new Reservation(null, scheduleId, memberId);
    }
}
