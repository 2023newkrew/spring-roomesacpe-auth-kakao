package nextstep.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAdminRequest {
    private Long scheduleId;
    private Long memberId;
}
