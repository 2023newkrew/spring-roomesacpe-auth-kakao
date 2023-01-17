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
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void createToken() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        String accessToken = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
        assertThat(accessToken).isNotNull();
    }
}
