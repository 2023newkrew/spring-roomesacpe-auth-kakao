package nextstep.entity;

import java.util.Objects;

public class Reservation {
    private Long id;
    private final Schedule schedule;
    private final Member member;

    private Reservation(ReservationBuilder builder) {
        this.schedule = builder.schedule;
        this.member = builder.member;
    }

    public static Reservation giveId(Reservation reservation, Long id) {
        reservation.id = id;
        return reservation;
    }
    public static ReservationBuilder builder(){
        return new ReservationBuilder();
    }

    public static class ReservationBuilder {

        private ReservationBuilder(){};

        private Schedule schedule;

        private Member member;

        public ReservationBuilder schedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public ReservationBuilder member(Member member) {
            this.member = member;
            return this;
        }

        public Reservation build() {
            validate();
            return new Reservation(this);
        }

        private void validate() {
            if (Objects.isNull(schedule)) {
                throw new IllegalArgumentException("schedule은 null일 수 없습니다.");
            }

            if (Objects.isNull(member)) {
                throw new IllegalArgumentException("member는 null일 수 없습니다.");
            }
        }

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
}
