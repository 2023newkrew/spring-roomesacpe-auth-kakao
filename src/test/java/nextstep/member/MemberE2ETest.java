package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MemberE2ETest {
    private static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    @DisplayName("멤버를 생성한다")
    @Test
    void create() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("개인정보를 가져온다")
    @Test
    void getMyInfo() {
        createNewMember();
        String accessToken = getAccessToken();

        var response = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat((String) response.jsonPath().get("username")).isEqualTo(USERNAME);
        assertThat((String) response.jsonPath().get("password")).isEqualTo(PASSWORD);
        assertThat((String) response.jsonPath().get("name")).isEqualTo(NAME);
        assertThat((String) response.jsonPath().get("phone")).isEqualTo(PHONE);
    }

    @DisplayName("개인정보를 가져올 때 토큰이 없으면 401 반환한다.")
    @Test
    void getMyInfoWithoutToken() {
        createNewMember();

        RestAssured
                .given().log().all()
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    void createNewMember() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    String getAccessToken() {
        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        return tokenResponse.body().as(TokenResponse.class).getAccessToken();
    }

}
