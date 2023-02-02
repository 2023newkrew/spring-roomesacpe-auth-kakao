package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.dto.auth.TokenRequest;
import nextstep.dto.member.MemberRequest;
import nextstep.dto.schedule.ScheduleRequest;
import nextstep.dto.theme.ThemeRequest;
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
    private static final long THEME_ID = 1L;
    private static final String THEME_NAME = "테마이름";
    private static final String THEME_DESC = "테마설명";
    private static final int THEME_PRICE = 22000;
    private static final String SCHEDULE_DATE = "2022-08-11";
    private static final String SCHEDULE_TIME = "13:00";
    private static final String WRONG_SCHEDULE_DATE = "2022-08-51";
    private static final String WRONG_SCHEDULE_TIME = "25:00";
    private static final long NOT_EXIST_THEME_ID = 1000L;
    private static final long MINUS_THEME_ID = -1L;
    private static final String NOT_EXIST_SCHEDULE_DATE = "2023-01-01";
    private static final long NOT_EXIST_SCHEDULE_ID = 1000L;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";
    private static final String NORMAL_USERNAME = "username";
    private static final String NORMAL_PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE = "010-1234-5678";
    private Long themeId;
    private String adminToken;
    private String normalToken;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(NORMAL_USERNAME, NORMAL_PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        adminToken = loginAndGetAccessToken(ADMIN_USERNAME, ADMIN_PASSWORD);
        normalToken = loginAndGetAccessToken(NORMAL_USERNAME,NORMAL_PASSWORD);

        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);

        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(themeRequest)
                .when().post("/admin/themes")
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
                .header("authorization", adminToken)
                .body(body)
                .when().post("/admin/schedules")
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
                .header("authorization", adminToken)
                .body(body)
                .when().post("/admin/schedules")
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
                .header("authorization", adminToken)
                .body(body)
                .when().post("/admin/schedules")
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
                .header("authorization", adminToken)
                .body(body)
                .when().post("/admin/schedules")
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
                .header("authorization", adminToken)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .header("authorization", adminToken)
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
                .header("authorization", adminToken)
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
                .header("authorization", adminToken)
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
                .header("authorization", adminToken)
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
                .header("authorization", adminToken)
                .when().delete("/admin/schedules/" + NOT_EXIST_SCHEDULE_ID)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("일반 사용자는 스케줄을 생성할 수 없다")
    @Test
    public void normalUserCannotCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(themeId, SCHEDULE_DATE, SCHEDULE_TIME);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", normalToken)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("일반 사용자는 스케줄을 삭제할 수 없다")
    @Test
    void normalUserCannotDeleteSchedule() {
        String location = requestCreateSchedule();

        RestAssured
                .given().log().all()
                .header("authorization", normalToken)
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("일반 사용자도 스케줄을 조회한다")
    @Test
    public void showNormalUserSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .header("authorization", normalToken)
                .param("themeId", themeId)
                .param("date", SCHEDULE_DATE)
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    public String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(THEME_ID, SCHEDULE_DATE, SCHEDULE_TIME);
        return RestAssured
                .given().log().all()
                .header("authorization", adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }

    private String loginAndGetAccessToken(String username, String password) {
        TokenRequest body = new TokenRequest(username, password);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return "Bearer " + accessToken.toString();
    }
}
