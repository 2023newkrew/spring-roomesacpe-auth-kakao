package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import javax.swing.Spring;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AdminE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 관리자는_처음_시작할_때_등록된다() {
        TokenRequest tokenRequest = new TokenRequest(1L, "password");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
