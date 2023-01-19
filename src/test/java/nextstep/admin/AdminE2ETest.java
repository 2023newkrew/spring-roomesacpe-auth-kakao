package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.reservation.ReservationE2ETest;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.DataLoader.ADMIN_MEMBER;
import static nextstep.auth.AuthE2ETest.createMemberToken;
import static nextstep.reservation.ReservationE2ETest.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminE2ETest {

    @DisplayName("어드민 권한으로 테마를 생성할 수 있다.")
    @Test
    void createTheme() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        String adminToken = createAdminToken();

        // when & then
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .auth().oauth2(adminToken)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        assertThat(themeResponse).isNotNull();
    }

    @DisplayName("일반 멤버 권한으로는 테마를 생성할 수 없다.")
    @Test
    void createThemeWithMember() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        String memberToken = createMemberToken();

        // when & then
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .auth().oauth2(memberToken)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("토큰이 없다면 테마를 생성할 수 없다.")
    @Test
    void createThemeWithNoAuth() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);

        // when & then
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("어드민 권한으로 테마를 삭제할 수 있다.")
    @Test
    void deleteTheme() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);
        String adminToken = createAdminToken();

        // when & then
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .when().delete("/admin/themes/" + themeId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("일반 멤버 권한으로는 테마를 삭제할 수 없다.")
    @Test
    void deleteThemeWithMember() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);
        String memberToken = createMemberToken();

        // when & then
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(memberToken)
                .when().delete("/admin/themes/" + themeId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰이 없다면 테마를 삭제할 수 없다.")
    @Test
    void deleteThemeWithNoToken() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        // when & then
        var response = RestAssured
                .given().log().all()
                .when().delete("/admin/themes/" + themeId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("어드민 권한으로 스케줄을 생성할 수 있다.")
    @Test
    void createSchedule() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        String adminToken = createAdminToken();

        // when & then
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .auth().oauth2(adminToken)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        assertThat(scheduleResponse).isNotNull();
    }

    @DisplayName("일반 멤버 권한으로는 스케줄을 생성할 수 없다.")
    @Test
    void createScheduleWithMember() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        String memberToken = createMemberToken();

        // when & then
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .auth().oauth2(memberToken)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("토큰이 없다면 스케줄을 생성할 수 없다.")
    @Test
    void createScheduleWithNoToken() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);

        // when & then
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("어드민 권한으로 예약을 삭제할 수 있다.")
    @Test
    void deleteSchedule() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        Long scheduleId = createScheduleWithAdminAuthority(scheduleRequest);
        String adminToken = createAdminToken();

        // when & then
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .when().delete("/admin/schedules/" + scheduleId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("멤버 권한으로는 예약을 삭제할 수 없다.")
    @Test
    void deleteScheduleWithMember() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        Long scheduleId = createScheduleWithAdminAuthority(scheduleRequest);
        String memberToken = createMemberToken();

        // when & then
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(memberToken)
                .when().delete("/admin/schedules/" + scheduleId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰이 없다면 예약을 삭제할 수 없다.")
    @Test
    void deleteScheduleWithNoToken() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        Long themeId = createThemeWithAdminAuthority(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        Long scheduleId = createScheduleWithAdminAuthority(scheduleRequest);

        // when & then
        var response = RestAssured
                .given().log().all()
                .when().delete("/admin/schedules/" + scheduleId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public static String createAdminToken() {
        TokenRequest body = new TokenRequest(ADMIN_MEMBER.getUsername(), ADMIN_MEMBER.getPassword());
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }

    public static Long createThemeWithAdminAuthority(ThemeRequest themeRequest) {
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .auth().oauth2(createAdminToken())
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        return Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    public static Long createScheduleWithAdminAuthority(ScheduleRequest scheduleRequest) {
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .auth().oauth2(createAdminToken())
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        return Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);
    }
}
