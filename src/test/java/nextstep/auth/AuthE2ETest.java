package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.support.DatabaseCleaner;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private Long memberId;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        databaseCleaner.insertInitialData();
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void create() {
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

    @DisplayName("내 정보를 조회한다")
    @Test
    void showMyInfo() {
        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        String accessToken = tokenResponse.body().as(TokenResponse.class).getAccessToken();

        var myInfoResponse = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat((String) myInfoResponse.jsonPath().get("username")).isEqualTo(USERNAME);
        assertThat((String) myInfoResponse.jsonPath().get("password")).isEqualTo(PASSWORD);
        assertThat((String) myInfoResponse.jsonPath().get("name")).isEqualTo("name");
        assertThat((String) myInfoResponse.jsonPath().get("phone")).isEqualTo("010-1234-5678");
    }
}
