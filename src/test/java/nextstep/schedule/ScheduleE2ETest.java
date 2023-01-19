package nextstep.schedule;

import io.restassured.RestAssured;
import java.util.List;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.Constant.ADMIN_PASSWORD;
import static nextstep.Constant.ADMIN_USERNAME;
import static nextstep.Constant.AUTHORIZATION;
import static nextstep.Constant.BEARER_TYPE;
import static nextstep.Constant.NAME;
import static nextstep.Constant.PASSWORD;
import static nextstep.Constant.PHONE;
import static nextstep.Constant.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static nextstep.Constant.THEME_NAME;
import static nextstep.Constant.THEME_DESCRIPTION;
import static nextstep.Constant.THEME_PRICE;
import static nextstep.Constant.DATE;
import static nextstep.Constant.TIME;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleE2ETest {
    private String adminToken;
    private Long themeId;

    @BeforeEach
    void setUp() {
        TokenRequest tokenRequest = new TokenRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        TokenResponse tokenResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);
        adminToken = tokenResponse.getAccessToken();

        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        String location = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(themeRequest)
                .header(AUTHORIZATION, BEARER_TYPE + adminToken)
                .when().post("/admin/themes")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        String[] themeLocation = location.split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    @DisplayName("스케줄을 생성한다")
    @Test
    void createSchedule() {
        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        String location = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(scheduleRequest)
                .header(AUTHORIZATION, BEARER_TYPE + adminToken)
                .when().post("/admin/schedules")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        int scheduleId = Integer.parseInt(location.split("/")[2]);
        assertThat(scheduleId).isPositive();
    }

    @DisplayName("로그인하지 않은 경우 스케줄을 생성할 수 없다")
    @Test
    void createScheduleUnauthorized() {
        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 계정이 아닌 경우 스케줄을 생성할 수 없다")
    @Test
    void createScheduleForbidden() {
        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        String otherToken = createToken();
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(scheduleRequest)
                .header(AUTHORIZATION, BEARER_TYPE + otherToken)
                .when().post("/admin/schedules")
                .then().statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("스케줄 목록을 조회한다")
    @Test
    void showSchedules() {
        int scheduleId = getCreatedScheduleId();

        var response = RestAssured
                .given().param("themeId", themeId)
                .param("date", DATE)
                .when().get("/schedules")
                .then().statusCode(HttpStatus.OK.value())
                .extract();

        List<Schedule> schedules = response.jsonPath().getList(".", Schedule.class);
        assertThat(schedules).hasSize(1);

        Schedule schedule = schedules.get(0);
        assertThat(schedule.getTheme().getId()).isEqualTo(themeId);
        assertThat(schedule.getId()).isEqualTo(scheduleId);
        assertThat(schedule.getDate()).isEqualTo(DATE);
        assertThat(schedule.getTime()).isEqualTo(TIME);
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void deleteSchedule() {
        int id = getCreatedScheduleId();
        RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + adminToken)
                .when().delete("/admin/schedules/" + id)
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("로그인하지 않은 경우 스케줄을 삭제할 수 없다")
    @Test
    void deleteScheduleUnauthorized() {
        int id = getCreatedScheduleId();
        RestAssured
                .given().log().all()
                .when().delete("/admin/schedules/" + id)
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 계정이 아닌 경우 스케줄을 삭제할 수 없다")
    @Test
    void deleteScheduleForbidden() {
        int id = getCreatedScheduleId();
        String otherToken = createToken();
        RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + otherToken)
                .when().delete("/admin/schedules/" + id)
                .then().statusCode(HttpStatus.FORBIDDEN.value());
    }

    private int getCreatedScheduleId() {
        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        String location = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(scheduleRequest)
                .header(AUTHORIZATION, BEARER_TYPE + adminToken)
                .when().post("/admin/schedules")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Integer.parseInt(location.split("/")[2]);
    }

    private String createToken() {
        MemberRequest memberRequestBody = MemberRequest.builder()
                .username(USERNAME).password(PASSWORD).name(NAME).phone(PHONE).build();

        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(memberRequestBody)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value());

        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);

        TokenResponse tokenResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);

        return tokenResponse.getAccessToken();
    }
}
