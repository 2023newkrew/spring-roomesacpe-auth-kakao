package nextstep.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import nextstep.member.MemberInfoResponse;

public class ReservationResponse {

    @JsonProperty("reservationId")
    private final Long id;

    @JsonUnwrapped
    private ScheduleResponse schedule;

    @JsonUnwrapped
    private MemberInfoResponse member;

    public ReservationResponse(Long id, ScheduleResponse schedule, MemberInfoResponse member) {
        this.id = id;
        this.schedule = schedule;
        this.member = member;
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), ScheduleResponse.of(reservation.getSchedule()),
                MemberInfoResponse.of(reservation.getMember()));
    }
}
