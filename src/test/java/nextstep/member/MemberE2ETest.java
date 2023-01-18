package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenConfig;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberE2ETest {

    @DisplayName("멤버 생성")
    @Test
    public void createMember() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @DisplayName("내 정보를 조회")
    @Test
    public void meTest() {
        String username = "username";
        String password = "password";

        // 멤버 등록
        MemberRequest memberRequest = new MemberRequest(username, password, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // token 가져오기
        TokenRequest tokenRequest = new TokenRequest(username, password);
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(TokenResponse.class);

        // token을 통해 멤버 정보 조회하기
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", JwtTokenConfig.TOKEN_CLASS + tokenResponse.getAccessToken())
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("username", equalTo("username"))
                .body("password", equalTo("password"))
                .body("name", equalTo("name"))
                .body("phone", equalTo("010-1234-5678"));
    }
}
