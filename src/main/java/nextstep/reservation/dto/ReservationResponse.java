package nextstep.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.Objects;
import nextstep.member.dto.MemberInfoResponse;
import nextstep.reservation.Reservation;
import nextstep.schedule.dto.ScheduleResponse;

public class ReservationResponse {

    @JsonProperty("reservationId")
    private final Long id;

    @JsonUnwrapped
    private final ScheduleResponse schedule;

    @JsonUnwrapped
    private final MemberInfoResponse member;

    public ReservationResponse(Long id, ScheduleResponse schedule, MemberInfoResponse member) {
        this.id = id;
        this.schedule = schedule;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public ScheduleResponse getSchedule() {
        return schedule;
    }

    public MemberInfoResponse getMember() {
        return member;
    }
}
