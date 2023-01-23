package nextstep.schedule;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.AcceptanceTestExecutionListener;
import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.auth.JwtTokenProviderTest.generateToken;
import static nextstep.auth.JwtTokenProviderTest.saveMember;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ScheduleTest {

    private static Long themeId;
    private String accessToken;
    private Reservation reservation;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        saveMember(jdbcTemplate);
        ExtractableResponse<Response> response = generateToken();
        accessToken = response.body().jsonPath().getString("accessToken");

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

    }

    @DisplayName("테마가 있는 경우 스케줄을 생성할 수 있음")
    @Test
    public void createSchedule() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마가 없는 스케줄을 생성하는 경우, 에러 발생")
    @Test
    void createErrorByTheme(){
        ScheduleRequest body = new ScheduleRequest(themeId+2, "2022-08-11", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.LENGTH_REQUIRED.value())
                .extract()
                .header("Location");
    }

    @DisplayName("스케줄을 조회할 수 있음")
    @Test
    public void showSchedules() {
        requestCreateSchedule();
        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .header("Authorization", "Bearer " + accessToken)
                .param("date", "2022-08-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("예약이 없는 경우 스케줄을 삭제할 수 있음")
    @Test
    void delete() {
        String location = requestCreateSchedule();
        var response = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().delete(location)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("예약이 되어있는 스케줄을 삭제하는 경우, 에러 발생")
    @Test
    void deleteErrorByReservation(){
        String location = requestCreateSchedule();
        reservation = new Reservation(
                new Schedule(
                        Long.parseLong(location.split("/")[2]),
                        new Theme(themeId,"테마이름", "테마설명", 22000),
                        LocalDate.parse("2022-08-11"),
                        LocalTime.parse("13:00")
                ),
                "testReservation"
        );
        reservationDao.save(reservation);
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("없는 스케줄을 삭제할 경우, 에러 발생")
    @Test
    void emptyDeleteTest(){
        String location = requestCreateSchedule() + "22";
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.LENGTH_REQUIRED.value());
    }


    private String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }
}
