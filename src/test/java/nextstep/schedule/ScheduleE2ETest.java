package nextstep.schedule;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.theme.ThemeRequest;
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

    private Long themeId;
    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        //어드민 멤버, 유저 멤버 생성
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MemberRequest("kayla", "password1", "kbpark", "010-1234-5678", "admin"))
                .when().post("/members")
                .then();

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MemberRequest("userA", "qwer12345", "june", "010-1234-5678", "user"))
                .when().post("/members")
                .then();

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

        //테마 생성
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
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
        requestCreateSchedule(adminToken);

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
