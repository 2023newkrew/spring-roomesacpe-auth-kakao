package nextstep.reservation.entity;

import nextstep.schedule.entity.Schedule;

public class Reservation {

    private Long id;
    private Schedule schedule;
    private Long memberId;

    public Reservation() {
    }

    public Reservation(Long id, Schedule schedule, Long memberId) {
        this.id = id;
        this.schedule = schedule;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Long getMemberId() {
        return memberId;
    }
}
