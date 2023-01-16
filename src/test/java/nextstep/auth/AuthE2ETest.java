package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.member.Member;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 생성한다")
    @Test
    public void create() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @DisplayName("내정보 조회하기")
    @Test
    public void showInformation() {
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();

        Member member = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(Member.class);

        assertThat(member.getPhone()).isEqualTo("010-1234-5678");
    }

    @DisplayName("잘못된 토큰")
    @Test
    public void invalidToken() {
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();

        RestAssured.given().log().all()
                .auth().oauth2("invalidtoken")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Disabled
    @DisplayName("db에 없는 사람 조회하기")
    @Test
    public void showInformation2() {
        TokenRequest body = new TokenRequest("asher", "1234");
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.as(TokenResponse.class)).isNull();
    }
}
