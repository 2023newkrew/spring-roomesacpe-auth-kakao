package nextstep.auth;

import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LoginUtils {
    public static String loginUser() {
        TokenRequest request = new TokenRequest("user", "user");
        return login(request);
    }

    public static String loginAdmin() {
        TokenRequest request = new TokenRequest("admin", "admin");
        return login(request);
    }

    private static String login(TokenRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
    }
}
