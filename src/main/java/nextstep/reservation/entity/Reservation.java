package nextstep.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.entity.Schedule;

@Getter
@NoArgsConstructor
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
