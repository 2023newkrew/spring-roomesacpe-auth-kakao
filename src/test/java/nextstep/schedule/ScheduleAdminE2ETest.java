package nextstep.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.auth.E2ETestAuthUtils;
import nextstep.member.E2ETestMemberUtils;
import nextstep.schedule.dto.request.ScheduleRequest;
import nextstep.theme.E2ETestThemeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleAdminE2ETest {

    private Long themeId;

    @BeforeEach
    void setUp() {
        themeId = E2ETestThemeUtils.createTheme();
    }

    @DisplayName("관리자 권한으로 스케줄을 생성한다")
    @Test
    public void create() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("일반 유저 권한으로 스케줄을 생성할 수 없다.")
    @Test
    public void createWithUserAuthority() {
        E2ETestMemberUtils.createMember();
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");
        String accessToken = E2ETestAuthUtils.loginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그인을 하지 않으면 스케줄을 생성할 수 없다.")
    @Test
    public void createWithNoneAuthority() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 권한으로 스케줄을 삭제한다")
    @Test
    void delete() {
        Long generatedId = E2ETestScheduleUtils.createSchedule(themeId);
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/schedules/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    @DisplayName("일반 유저 권한으로 스케줄을 삭제할 수 없다.")
    @Test
    void deleteWithUserAuthority() {
        E2ETestMemberUtils.createMember();
        Long generatedId = E2ETestScheduleUtils.createSchedule(themeId);
        String accessToken = E2ETestAuthUtils.loginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/schedules/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @DisplayName("로그인을 하지 않으면 스케줄을 삭제할 수 없다.")
    @Test
    void deleteWithNoneAuthority() {
        Long generatedId = E2ETestScheduleUtils.createSchedule(themeId);

        RestAssured
                .given().log().all()
                .when().delete("/admin/schedules/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("관리자 권한으로 스케줄을 조회한다 (일반 api 테스트)")
    @Test
    public void showSchedules() {
        E2ETestScheduleUtils.createSchedule(themeId);
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .param("themeId", themeId)
                .param("date", "2022-08-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }
}
