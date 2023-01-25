package nextstep.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.auth.E2ETestAuthUtils;
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

    @DisplayName("관리자 권한으로 스케줄을 삭제한다")
    @Test
    void delete() {
        Long generatedId = E2ETestScheduleUtils.createSchedule(themeId);
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/schedules/" + generatedId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


}
