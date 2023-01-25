package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleE2ETest {

    private Long themeId;
    private String token;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.createToken("admin1");

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
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
    void createSchedule() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("존재하지 않는 테마로 스케줄을 생성하면 404 코드 반환")
    @Test
    void createSchedule_fail() {
        ScheduleRequest body = new ScheduleRequest(-1L, "2022-08-11", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(ErrorCode.THEME_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.THEME_NOT_FOUND.getCode()));
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    void showSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", "2022-08-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".")).hasSize(1);
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void delete() {
        String location = requestCreateSchedule();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/admin" + location)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("일반 사용자가 스케줄 생성 요청 시 404 코드 반환")
    @Test
    void createSchedule_unauthorized() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");

        String token = jwtTokenProvider.createToken("user");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(ErrorCode.USER_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.USER_NOT_FOUND.getCode()));
    }

    private String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(1L, "2022-08-11", "13:00");
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }
}
