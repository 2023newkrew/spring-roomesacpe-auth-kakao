package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String PHONE = "010-1234-5678";

    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        MemberRequest body = new MemberRequest(USERNAME, "password", NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("본인 정보를 조회한다")
    @Test
    public void me() {
        create();
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.createToken(USERNAME, MemberRole.USER);
        RestAssured
                .given().header("authorization", "Bearer " + token).log().all()
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("username", is(USERNAME))
                .body("name", is(NAME))
                .body("phone", is(PHONE));
    }

}
