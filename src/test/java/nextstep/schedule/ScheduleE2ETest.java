package nextstep.schedule;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test.sql"})
public class ScheduleE2ETest {

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {

        //어드민 멤버, 유저 멤버 로그인
        adminToken = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("kayla", "password1"))
                .when().post("/login/token")
                .then()
                .extract().as(TokenResponse.class).getAccessToken();

        userToken = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("userA", "qwer12345"))
                .when().post("/login/token")
                .then()
                .extract().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("스케줄을 생성한다")
    @Test
    public void createSchedule() {
        ExtractableResponse response = requestCreateSchedule(adminToken);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("관리자가 아닌 멤버는 스케줄을 생성할 수 없다")
    @Test
    public void rejectCreateSchedule() {
        ExtractableResponse response = requestCreateSchedule(userToken);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {

        var response = RestAssured
                .given().log().all()
                .param("themeId", 3)
                .param("date", "2023-02-01")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("관리자가 아닌 멤버는 스케줄을 삭제할 수 없다")
    @Test
    void delete() {
        String location = requestCreateSchedule(adminToken).header("Location");
        ExtractableResponse response = requestDeleteSchedule(userToken, "/admin" + location);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void rejectDelete() {
        String location = requestCreateSchedule(adminToken).header("Location");
        ExtractableResponse response = requestDeleteSchedule(adminToken, "/admin" + location);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse requestCreateSchedule(String token) {
        ScheduleRequest body = new ScheduleRequest(1L, "2022-08-11", "13:00");
        return RestAssured
                .given()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then()
                .extract();
    }

    private ExtractableResponse requestDeleteSchedule(String token, String location) {
        return RestAssured
                .given()
                .auth().oauth2(token)
                .when().delete(location)
                .then()
                .extract();
    }
}
