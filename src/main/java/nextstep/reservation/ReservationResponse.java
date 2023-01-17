package nextstep.reservation;

import nextstep.schedule.Schedule;

public class ReservationResponse {
    private long id;
    private Schedule schedule;
    private long memberId;

    public ReservationResponse(long id, Schedule schedule, long memberId) {
        this.id = id;
        this.schedule = schedule;
        this.memberId = memberId;
    }

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.schedule = reservation.getSchedule();
        this.memberId = reservation.getMemberId();
    }

    public long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public long getMemberId() {
        return memberId;
    }
}
