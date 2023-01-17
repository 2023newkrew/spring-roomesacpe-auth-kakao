package nextstep.auth;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import nextstep.member.Member;
import nextstep.member.MemberUtil;
import org.springframework.http.MediaType;

public class AuthUtil {
    public static final TokenRequest RESERVATION_EXIST_USER_TOKEN_REQUEST = new TokenRequest(MemberUtil.RESERVATION_EXIST_MEMBER_1.getUsername(), MemberUtil.RESERVATION_EXIST_MEMBER_1.getPassword());
    public static final TokenRequest RESERVATION_NOT_EXIST_USER_TOKEN_REQUEST = new TokenRequest(MemberUtil.RESERVATION_NOT_EXIST_MEMBER.getUsername(), MemberUtil.RESERVATION_NOT_EXIST_MEMBER.getPassword());

    public static TokenResponse createTokenForReservationExistUser() {
        return createToken(RESERVATION_EXIST_USER_TOKEN_REQUEST);
    }

    public static TokenResponse createTokenForReservationNotExistUser() {
        return createToken(RESERVATION_NOT_EXIST_USER_TOKEN_REQUEST);
    }

    public static TokenResponse createToken(Member member) {
        return createToken(new TokenRequest(member.getUsername(), member.getPassword()));
    }

    public static TokenResponse createToken(TokenRequest tokenRequest) {
        return createTokenAndGetValidatableResponse(tokenRequest)
                .extract().as(TokenResponse.class);
    }

    public static TokenRequest getNotExistUserTokenRequest(){
        return new TokenRequest(MemberUtil.NOT_EXIST_MEMBER.getUsername(), MemberUtil.NOT_EXIST_MEMBER.getPassword());
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
