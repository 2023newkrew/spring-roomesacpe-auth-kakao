package nextstep.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nextstep.member.Member;
import nextstep.schedule.Schedule;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private Schedule schedule;
    private Member member;

    public Reservation(Schedule schedule, Member member) {
        this.id = null;
        this.schedule = schedule;
        this.member = member;
    }
}
