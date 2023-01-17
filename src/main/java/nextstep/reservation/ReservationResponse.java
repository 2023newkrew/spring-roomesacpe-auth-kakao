package nextstep.reservation;

import nextstep.schedule.Schedule;

public class ReservationResponse {
    private Long id;
    private Schedule schedule;
    private String name;

    public ReservationResponse() {
    }

    public ReservationResponse(Long id, Schedule schedule, String name) {
        this.id = id;
        this.schedule = schedule;
        this.name = name;
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getSchedule(), reservation.getName());
    }

}
