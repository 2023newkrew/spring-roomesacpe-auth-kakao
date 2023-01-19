package nextstep.reservation;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.Schedule;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation {
    private Long id;
    private Schedule schedule;
    private String name;

    private Long memberId;

    public Reservation(Schedule schedule, String name, Long memberId) {
        this(null, schedule, name, memberId);
    }

    public boolean isMine(Long memberId) {
        return Objects.equals(this.memberId, memberId);
    }
}
