package nextstep.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationRequest {
    private Long scheduleId;
    @Setter
    private String username;

    public ReservationRequest(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
