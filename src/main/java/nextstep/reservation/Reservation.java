package nextstep.reservation;

import nextstep.schedule.Schedule;

public class Reservation {

    private Long id;

    private Schedule schedule;

    private String username;

    public Reservation() {
    }

    public Reservation(Schedule schedule, String username) {
        this.schedule = schedule;
        this.username = username;
    }

    public Reservation(Long id, Schedule schedule, String username) {
        this.id = id;
        this.schedule = schedule;
        this.username = username;
    }

    public boolean isOwner(String username) {
        return this.username.equals(username);
    }

    public Long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getUsername() {
        return username;
    }
}
