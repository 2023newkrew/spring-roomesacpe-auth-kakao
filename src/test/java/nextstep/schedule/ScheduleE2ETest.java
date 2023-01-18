package nextstep.schedule;

import nextstep.auth.AuthTestUtil;
import nextstep.auth.model.TokenResponse;
import nextstep.member.model.Member;
import nextstep.member.MemberTestUtil;
import nextstep.schedule.model.Schedule;
import nextstep.schedule.model.ScheduleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleE2ETest {

    @DisplayName("인증된 사용자는 스케줄을 생성할 수 있다.")
    @Test
    void test1() {
        Member ReservationExistUser = MemberTestUtil.getReservationExistMember(1L);
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(ReservationExistUser);

        ScheduleRequest schedule = new ScheduleRequest(1L, "2022-08-11", "13:00");
        ScheduleTestUtil.createScheduleAndGetValidatableResponse(schedule, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("인증되지 않은 사용자는 스케줄을 생성할 수 없다.")
    @Test
    void test2() {
        ScheduleRequest schedule = new ScheduleRequest(1L, "2022-08-11", "13:00");
        ScheduleTestUtil.createScheduleAndGetValidatableResponse(schedule, "")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("인증되지 않은 사용자는 스케줄을 조회할 수 있다.")
    @Test
    void test3() {
        List<Schedule> schedules = ScheduleTestUtil.getSchedules(1L, "2022-11-11");

        assertThat(schedules).hasSize(6);
    }

    @DisplayName("인증된 사용자는 스케줄을 삭제할 수 있다.")
    @Test
    void test4() {
        Member ReservationExistUser = MemberTestUtil.getReservationExistMember(1L);
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(ReservationExistUser);

        ScheduleTestUtil.deleteScheduleAndGetValidatableResponse(2L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("인증되지 않은 사용자는 스케줄을 삭제할 수 없다.")
    @Test
    void test5() {
        ScheduleTestUtil.deleteScheduleAndGetValidatableResponse(2L, "")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
