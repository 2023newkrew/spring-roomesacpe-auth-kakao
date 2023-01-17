package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {
    private static final String USERNAME = "email@email.com";
    private static final String PASSWORD = "1234";
    private static final String NAME = "name";
    private static final String PHONE = "010-1234-5678";


    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }


    @DisplayName("멤버를 조회한다")
    @Test
    public void find() {
        String accessToken = getToken();
        MemberResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .extract().as(MemberResponse.class);

        assertThat(response.getPassword()).isEqualTo(PASSWORD);
    }


    String getToken() {
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();
    }
}
