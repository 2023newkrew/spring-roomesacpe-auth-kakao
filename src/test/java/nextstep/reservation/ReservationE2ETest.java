package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.member.Member;
import nextstep.member.MemberRequest;
import nextstep.member.Role;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.Theme;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    private String token;
    private final Member member = Member.builder()
            .username("username")
            .password("password")
            .name("name")
            .phone("010-1234-5678")
            .role(Role.MEMBER)
            .build();

    private final Theme theme = Theme.builder()
            .name("themeName")
            .desc("themeDesc")
            .price(22000)
            .build();

    private final Schedule schedule = Schedule.builder()
            .theme(theme)
            .date(LocalDate.parse("2022-08-11"))
            .time(LocalTime.parse("13:00"))
            .build();
    private final Reservation reservation = Reservation.builder()
            .schedule(schedule)
            .member(member)
            .build();

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(member);
        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        member.setId(Long.valueOf(location.split("/")[2]));
        token = jwtTokenProvider.createToken(new TokenRequest(member));

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        location = RestAssured
                .given().auth().oauth2(token).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        theme.setId(Long.parseLong(location.split("/")[2]));

        ScheduleRequest scheduleRequest = new ScheduleRequest(schedule);
        location = RestAssured
                .given().auth().oauth2(token).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        schedule.setId(Long.parseLong(location.split("/")[2]));

        ReservationRequest request = new ReservationRequest(schedule.getId(), member.getName());
        location = RestAssured
                .given().auth().oauth2(token).log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        reservation.setId(Long.parseLong(location.split("/")[2]));
    }

    @Test
    void 예약을_조회한다() {
        ExtractableResponse<Response> response = RestAssured
                .given().auth().oauth2(token).log().all()
                .param("themeId", theme.getId())
                .param("date", schedule.getDate().toString())
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void 예약을_삭제한다() {
        ExtractableResponse<Response> response = RestAssured
                .given().auth().oauth2(token).log().all()
                .when().delete("/reservations/" + reservation.getId())
                .then().log().all()
                .extract();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 중복_예약을_생성한다() {
        ReservationRequest request = new ReservationRequest(schedule.getId(), member.getName());
        var response = RestAssured
                .given().auth().oauth2(token).log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    @Test
    void showEmptyReservations() {
        예약을_삭제한다();
        var response = RestAssured
                .given().auth().oauth2(token).log().all()
                .param("themeId", theme.getId())
                .param("date", schedule.getDate().toString())
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @Test
    void 없는_예약을_삭제한다() {
        var response = RestAssured
                .given().auth().oauth2(token).log().all()
                .when().delete("/reservations/100")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
