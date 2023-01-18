package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.member.Role;
import nextstep.theme.ThemeRequest;
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
@Sql("/truncate.sql")
public class ScheduleE2ETest {

    private Long themeId;
    private String accessToken;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678", Role.ADMIN);
        var memberResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("username", "password"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00");
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", "2022-08-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void delete() {
        String location = requestCreateSchedule();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin" + location)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public String requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(1L, "2022-08-11", "13:00");
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
    }
}
