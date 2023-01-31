package nextstep.auth;

import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class AuthUtil {
    public static final String RESERVATION_EXIST_USERNAME = "reservation_exist_user";
    private static final String RESERVATION_EXIST_PASSWORD = "password";

    public static final String RESERVATION_NOT_EXIST_USERNAME = "reservation_exist_user";
    private static final String RESERVATION_NOT_EXIST_PASSWORD = "password";

    public static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    public static String createTokenForReservationExistUser() {
        return createToken(new TokenRequest(RESERVATION_EXIST_USERNAME, RESERVATION_EXIST_PASSWORD));
    }

    public static String createTokenForReservationNotExistUser() {
        return createToken(new TokenRequest(RESERVATION_NOT_EXIST_USERNAME, RESERVATION_NOT_EXIST_PASSWORD));
    }

    public static String createTokenForAdminUser() {
        return createToken(new TokenRequest(ADMIN_USERNAME, ADMIN_PASSWORD));
    }

    public static String createToken(TokenRequest tokenRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class)
                .getAccessToken();
    }
}
