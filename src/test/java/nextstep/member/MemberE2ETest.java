package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.jwt.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post("/members")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보를 조회한다")
    @Test
    void me() {
        String token = createToken();

        RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get("/members/me")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .body("username", is("username"));
    }

    String createToken() {
        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        TokenRequest tokenRequest = new TokenRequest("username", "password");

        RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when()
                .post("/members")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value());

        return RestAssured
                .given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when()
                .post("/login/token")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .get("accessToken");
    }
}
