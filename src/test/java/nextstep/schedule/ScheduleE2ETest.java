package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.auth.LoginUtils;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.auth.LoginUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleE2ETest {
    private String token;
    @Test
    @DisplayName("스케줄을 생성한다")
    public void createSchedule() {
        token = loginUser();

        ScheduleRequest body = new ScheduleRequest(9999L, "2022-08-15", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        token = loginUser();
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .param("themeId", 9999L)
                .param("date", "2022-08-15")
                .auth().oauth2(token)
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @Test
    @DisplayName("스케줄을 삭제한다")
    void delete() {
        token = loginUser();
        String location = requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete(location)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(9999L, "2022-08-15", "13:00");
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }
}
