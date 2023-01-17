package nextstep.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import nextstep.member.dto.MemberResponse;
import nextstep.reservation.Reservation;
import nextstep.schedule.dto.ScheduleResponse;

public class ReservationResponse {

    @JsonProperty("reservationId")
    private final Long id;

    @JsonUnwrapped
    private ScheduleResponse schedule;

    @JsonUnwrapped
    private MemberResponse member;

    private ReservationResponse(Long id, ScheduleResponse schedule, MemberResponse member) {
        this.id = id;
        this.schedule = schedule;
        this.member = member;
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                ScheduleResponse.of(reservation.getSchedule()),
                MemberResponse.of(reservation.getMember()));
    }

    public Long getId() {
        return id;
    }

    public ScheduleResponse getSchedule() {
        return schedule;
    }

    public MemberResponse getMember() {
        return member;
    }
}
