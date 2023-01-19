package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.DataLoader.ADMIN_MEMBER;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminE2ETest {

    public static String createAdminToken() {
        TokenRequest body = new TokenRequest(ADMIN_MEMBER.getUsername(), ADMIN_MEMBER.getPassword());
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }
}
