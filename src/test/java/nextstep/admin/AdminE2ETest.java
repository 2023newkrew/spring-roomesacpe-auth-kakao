package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.reservation.ReservationRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.support.DatabaseCleaner;
import nextstep.theme.ThemeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminE2ETest {

    @Autowired
    private DatabaseCleaner databaseCleaner;
    private String adminToken;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        databaseCleaner.insertInitialData();

        var adminTokenResponse = RestAssured
                .given().log().all()
                .when().get("/admin/token")
                .then().log().all()
                .extract();
        adminToken = adminTokenResponse.body().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("어드민 토큰을 발급받는다")
    @Test
    void getAdminToken() {
        var response = RestAssured
                .given().log().all()
                .when().get("/admin/token")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat((String) response.jsonPath().get("accessToken")).isNotNull();
    }

    @DisplayName("토큰 없이 어드민 페이지에 접근하면 401 반환")
    @Test
    void accessAdminPageWithoutAdminToken() {
        var response = RestAssured
                .given().log().all()
                .when().delete("/admin/themes/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("일반유저 토큰으로 어드민 페이지에 접근하면 403 반환")
    @Test
    void accessAdminPageWithCommonUserToken() {
        String commonUserToken = getCommonUserAccessToken();

        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + commonUserToken)
                .when().delete("/admin/themes/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("테마를 생성한다")
    @Test
    void createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + adminToken)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void deleteTheme() {
        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + adminToken)
                .when().delete("admin/themes/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("관리자는 다른 사람 예약을 삭제할 수 있다")
    @Test
    void delete() {
        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + adminToken)
                .when().delete("/admin/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void deleteSchedule() {
        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + adminToken)
                .when().delete("/admin/schedules/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private String getCommonUserAccessToken() {
        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("username", "password"))
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        return tokenResponse.body().as(TokenResponse.class).getAccessToken();
    }
}
