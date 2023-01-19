package nextstep.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.schedule.Schedule;

@Getter
@AllArgsConstructor
public class Reservation {

    private Long id;
    private Schedule schedule;
    private String name;

    public Reservation(Schedule schedule, String name) {
        this.schedule = schedule;
        this.name = name;
    }

}
