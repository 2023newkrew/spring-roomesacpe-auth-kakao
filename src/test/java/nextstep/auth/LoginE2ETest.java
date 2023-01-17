package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginE2ETest {

    @DisplayName("토큰을 생성한다")
    @Test
    public void createToken() {
        Member member = new Member("user", "pw", "user", "010-0000-0000");
        TokenRequest tokenRequest = new TokenRequest("user", "pw");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(member)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
