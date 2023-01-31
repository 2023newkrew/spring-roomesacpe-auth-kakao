package nextstep;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AbstractE2ETest {

    protected String createBearerToken(String username, String password) {
        TokenRequest request = new TokenRequest(username, password);

        TokenResponse response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/login/token")
                .then().log().all()
                .extract()
                .as(TokenResponse.class);

        return "Bearer " + response.getAccessToken();
    }
}
