package nextstep.common.fixture;

import io.restassured.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.MemberRequest;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class MemberProvider {

    public static ExtractableResponse<Response> 멤버를_생성한다(MemberRequest memberRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
        .when()
                .post("/members")
        .then()
                .extract();
    }

    public static TokenResponse 로그인을_한다(TokenRequest tokenRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
        .when()
                .post("/login/token")
        .then()
                .extract()
                .as(new TypeRef<TokenResponse>() {})
        ;
    }

}
