package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.domain.model.request.TokenRequest;
import nextstep.domain.model.response.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LoginUtils {
    public static String loginUser() {
        TokenRequest request = new TokenRequest("user", "user");
        return loginByRequest(request);
    }

    public static String loginAdmin() {
        TokenRequest request = new TokenRequest("admin", "admin");
        return loginByRequest(request);
    }

    public static String loginByRequest(TokenRequest request) {
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
