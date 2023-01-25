package nextstep.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import nextstep.schedule.ScheduleResponse;

public class ReservationResponse {

    private final Long id;
    private final ScheduleResponse schedule;
    private final String name;
    private final Long memberId;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.schedule = new ScheduleResponse(reservation.getSchedule());
        this.name = reservation.getName();
        this.memberId = reservation.getMemberId();
    }

    @JsonCreator
    public ReservationResponse(Long id, ScheduleResponse schedule, String name, Long memberId) {
        this.id = id;
        this.schedule = schedule;
        this.name = name;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public ScheduleResponse getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public Long getMemberId() {
        return memberId;
    }
}
