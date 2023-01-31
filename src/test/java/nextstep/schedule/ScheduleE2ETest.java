package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.theme.dto.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleE2ETest {
    public static final long THEME_ID = 1L;
    public static final String THEME_NAME = "테마이름";
    public static final String THEME_DESC = "테마설명";
    public static final int THEME_PRICE = 22000;
    public static final String SCHEDULE_DATE = "2022-08-11";
    public static final String SCHEDULE_TIME = "13:00";
    public static final String WRONG_SCHEDULE_DATE = "2022-08-51";
    public static final String WRONG_SCHEDULE_TIME = "25:00";
    public static final long NOT_EXIST_THEME_ID = 1000L;
    public static final long MINUS_THEME_ID = -1L;
    public static final String NOT_EXIST_SCHEDULE_DATE = "2023-01-01";
    public static final long NOT_EXIST_SCHEDULE_ID = 1000L;
    private Long themeId;

    @BeforeEach
    void setUp() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);

        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = response.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    @DisplayName("스케줄을 생성한다")
    @Test
    public void createSchedule() {
        ScheduleRequest body = new ScheduleRequest(themeId, SCHEDULE_DATE, SCHEDULE_TIME);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("없는 테마로 스케줄을 생성할 수 없다")
    @Test
    public void cannotCreateScheduleWithNotExistTheme() {
        ScheduleRequest body = new ScheduleRequest(NOT_EXIST_THEME_ID, SCHEDULE_DATE, SCHEDULE_TIME);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("음수 테마번호로 스케줄을 생성할 수 없다")
    @Test
    public void cannotCreateScheduleWithMinusThemeId() {
        ScheduleRequest body = new ScheduleRequest(MINUS_THEME_ID, SCHEDULE_DATE, SCHEDULE_TIME);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("잘못된 날짜 형식으로 스케줄을 생성할 수 없다")
    @Test
    public void cannotCreateScheduleWithWrongDate() {
        ScheduleRequest body = new ScheduleRequest(themeId, WRONG_SCHEDULE_DATE, SCHEDULE_TIME);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("잘못된 시간 형식으로 스케줄을 생성할 수 없다")
    @Test
    public void cannotCreateScheduleWithWrongTime() {
        ScheduleRequest body = new ScheduleRequest(themeId, SCHEDULE_DATE, WRONG_SCHEDULE_TIME);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", SCHEDULE_DATE)
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("없는 테마아이디로 조회하면 빈 배열을 얻는다.")
    @Test
    public void cannotShowNotExistThemeSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .param("themeId", NOT_EXIST_THEME_ID)
                .param("date", SCHEDULE_DATE)
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(0);
    }

    @DisplayName("없는 스케줄날짜로 조회하면 빈 배열을 얻는다.")
    @Test
    public void cannotShowNotExistDateSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .param("themeId", NOT_EXIST_THEME_ID)
                .param("date", NOT_EXIST_SCHEDULE_DATE)
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(0);
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void delete() {
        String location = requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .when().delete(location)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("없는 스케줄을 삭제할 수 없다.")
    @Test
    void cannotDeleteNotExistSchedule() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .when().delete("/schedules/" + NOT_EXIST_SCHEDULE_ID)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(THEME_ID, SCHEDULE_DATE, SCHEDULE_TIME);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }
}
