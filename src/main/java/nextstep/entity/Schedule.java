package nextstep.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule {
    private Long id;
    private final Theme theme;
    private final LocalDate date;
    private final LocalTime time;

    public static Schedule giveId(Schedule schedule, Long id) {
        schedule.id = id;
        return schedule;
    }

    public static ScheduleBuilder builder() {
        return new ScheduleBuilder();
    }


    public static class ScheduleBuilder {

        private Theme theme;

        private LocalDate date;
        private LocalTime time;
        public ScheduleBuilder theme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public ScheduleBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public ScheduleBuilder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public Schedule build() {
            validate();
            return new Schedule(this);
        }

        private void validate() {
            if (Objects.isNull(theme)) {
                throw new IllegalArgumentException("theme는 null일 수 없습니다.");
            }

            if (Objects.isNull(date)) {
                throw new IllegalArgumentException("date는 null일 수 없습니다.");
            }

            if (Objects.isNull(time)) {
                throw new IllegalArgumentException("time은 null일 수 없습니다.");
            }
        }


    }
    public Long getId() {
        return id;
    }

    public Theme getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    private Schedule(ScheduleBuilder builder) {
        this.theme = builder.theme;
        this.date = builder.date;
        this.time = builder.time;
    }
}
