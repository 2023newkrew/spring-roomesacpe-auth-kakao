package nextstep.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.dto.ScheduleResponse;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private ScheduleResponse schedule;
    private Long memberId;
}
