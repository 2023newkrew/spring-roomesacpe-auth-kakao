package nextstep.reservation;

import nextstep.member.Member;
import nextstep.schedule.Schedule;

public class Reservation {
    private Long id;
    private Schedule schedule;
    private Member member;
    private String name;

    public Reservation() {
    }

    public Reservation(Schedule schedule, Member member, String name) {
        this(null, schedule, member, name);
    }

    public Reservation(Long id, Schedule schedule, Member member, String name) {
        this.id = id;
        this.schedule = schedule;
        this.member = member;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return name;
    }
}
