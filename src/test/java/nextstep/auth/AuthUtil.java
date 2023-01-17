package nextstep.auth;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

public class AuthUtil {
    private static final String RESERVATION_EXIST_USERNAME = "reservation_exist_user";
    private static final String RESERVATION_EXIST_PASSWORD = "password";
    private static final String RESERVATION_NOT_EXIST_USERNAME = "reservation_exist_user";
    private static final String RESERVATION_NOT_EXIST_PASSWORD = "password";

    public static final TokenRequest RESERVATION_EXIST_USER_TOKEN_REQUEST = new TokenRequest(RESERVATION_EXIST_USERNAME, RESERVATION_EXIST_PASSWORD);
    public static final TokenRequest RESERVATION_NOT_EXIST_USER_TOKEN_REQUEST = new TokenRequest(RESERVATION_NOT_EXIST_USERNAME, RESERVATION_NOT_EXIST_PASSWORD);

    public static TokenResponse createTokenForReservationExistUser() {
        return createToken(RESERVATION_EXIST_USER_TOKEN_REQUEST);
    }

    public static TokenResponse createTokenForReservationNotExistUser() {
        return createToken(RESERVATION_NOT_EXIST_USER_TOKEN_REQUEST);
    }

    public static TokenResponse createToken(TokenRequest tokenRequest) {
        return createTokenAndGetValidatableResponse(tokenRequest)
                .extract().as(TokenResponse.class);
    }

    public static ValidatableResponse createTokenAndGetValidatableResponse(TokenRequest tokenRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all();
    }
}
