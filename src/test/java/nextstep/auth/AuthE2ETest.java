package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.Constant.USERNAME;
import static nextstep.Constant.PASSWORD;
import static nextstep.Constant.NAME;
import static nextstep.Constant.PHONE;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthE2ETest {

    @BeforeEach
    void setUp() {
        MemberRequest body = MemberRequest.builder()
                .username(USERNAME).password(PASSWORD).name(NAME).phone(PHONE).build();
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void createToken() {
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);
        String accessToken = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
        assertThat(accessToken).isNotNull();
    }

    @DisplayName("없는 유저명으로는 토큰을 생성하지 못한다")
    @Test
    void createTokenWithNonExistentUsernameFails() {
        String wrongUserName = "wrong";
        TokenRequest tokenRequest = new TokenRequest(wrongUserName, PASSWORD);
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("잘못된 비밀번호로는 토큰을 생성하지 못한다")
    @Test
    void createTokenWithWrongPasswordFails() {
        String wrongPassword = "wrong";
        TokenRequest tokenRequest = new TokenRequest(USERNAME, wrongPassword);
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
