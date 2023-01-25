package nextstep.member;

import io.restassured.RestAssured;
import nextstep.member.dto.request.MemberRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class E2ETestMemberUtils {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static Long createMember() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678");
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
