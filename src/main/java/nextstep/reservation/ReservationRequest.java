package nextstep.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationRequest {
    @NotBlank
    private Long scheduleId;

    @Setter
    private String username;

    public ReservationRequest(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
