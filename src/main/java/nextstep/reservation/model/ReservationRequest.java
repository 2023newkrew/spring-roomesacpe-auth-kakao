package nextstep.reservation.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    @NotNull
    private Long scheduleId;

    @Setter
    private String memberName;

    public ReservationRequest(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
