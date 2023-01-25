package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class E2ETestAuthUtils {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    public static String loginAndGetAccessToken() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return accessToken.toString();
    }

    public static String adminLoginAndGetAccessToken() {
        TokenRequest body = new TokenRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return accessToken.toString();
    }
}
