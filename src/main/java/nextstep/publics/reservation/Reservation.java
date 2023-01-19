package nextstep.publics.reservation;

import nextstep.framework.exception.AuthErrorCode;
import nextstep.framework.exception.BusinessException;
import nextstep.publics.schedule.Schedule;

public class Reservation {
    private Long id;
    private Schedule schedule;
    private String name;

    public Reservation() {
    }

    public Reservation(Schedule schedule, String name) {
        this.schedule = schedule;
        this.name = name;
    }

    public Reservation(Long id, Schedule schedule, String name) {
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

    public void checkOwnerOnDelete(String username) {
        if (!this.name.equals(username)) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED_DELETE);
        }
    }
}
