package nextstep.setup;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class TestSetUp {
    public static String getAdminToken() {
        TokenRequest adminTokenRequest = new TokenRequest("admin", "admin");
        TokenResponse adminTokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(adminTokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .getBody()
                .as(TokenResponse.class);

        return adminTokenResponse.getAccessToken();
    }

    public static Long registerTheme(String adminToken) {
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] themeLocation = themeResponse.header("Location").split("/");
        return Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    public static Long registerSchedule(Long themeId) {
        final String DATE = "2022-08-11";
        final String TIME = "13:00";

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        return Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);
    }

    public static Long registerMember() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        var memberResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] memberLocation = memberResponse.header("Location").split("/");
        return Long.parseLong(memberLocation[memberLocation.length - 1]);
    }

    public static String getUserToken() {
        TokenRequest tokenRequest = new TokenRequest("username", "password");
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .getBody()
                .as(TokenResponse.class);

        return tokenResponse.getAccessToken();
    }
}
