package nextstep.reservation;

import nextstep.schedule.Schedule;

public class ReservationResponse {
    private Long id;
    private Schedule schedule;
    private String name;

    public ReservationResponse(Long id, Schedule schedule, String name) {
        this.id = id;
        this.schedule = schedule;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }
}
