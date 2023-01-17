package nextstep.member;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

public class MemberUtil {
    public static final Member RESERVATION_EXIST_MEMBER = new Member("reservation_exist_user", "password", "name", "010-1234-5678");
    public static final Member RESERVATION_NOT_EXIST_MEMBER = new Member("'no_reservation_exist_user'", "'password'", "name", "010-1234-5678");

    public static final Member NOT_EXIST_MEMBER = new Member("NOT_EXIST_USERNAME", "password", "name", "010-1234-5678");

    public static Response createMember(MemberRequest memberRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members");
    }

    public static ValidatableResponse createMemberAndGetValidatableResponse(MemberRequest memberRequest) {
        return createMember(memberRequest)
                .then().log().all();
    }


    public static Member getMemberSelfInfo(String accessToken) {
        return getMemberSelfInfoAndGetValidatableResponse(accessToken)
                .extract().as(Member.class);
    }

    public static ValidatableResponse getMemberSelfInfoAndGetValidatableResponse(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all();
    }
}
