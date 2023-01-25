package nextstep.reservations.domain.entity.reservation;

import nextstep.reservations.domain.entity.theme.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(final Long id, final LocalDate date, final LocalTime time, final String name, final Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public static ReservationBuilder builder() {
        return new ReservationBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public static class ReservationBuilder {
        private Long id;
        private LocalDate date;
        private LocalTime time;
        private String name;
        private Theme theme;

        ReservationBuilder() {
        }

        public ReservationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReservationBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public ReservationBuilder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public ReservationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ReservationBuilder theme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, date, time, name, theme);
        }
    }
}
