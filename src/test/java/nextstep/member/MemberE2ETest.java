package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.dto.AccessTokenResponse;
import nextstep.auth.dto.AuthRequest;
import nextstep.member.dto.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.core.Is.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String PHONE = "010-1234-5678";

    private MemberRequest memberRequest;

    @BeforeEach
    void setUp() {
        memberRequest = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
    }

    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void show() {
        String accessToken = createMemberAndGetToken();
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("username", is(USERNAME))
                .body("name", is(NAME))
                .body("phone", is(PHONE));
    }

    private String createMemberAndGetToken() {
        RestAssured
                .given().log().all()
                .body(memberRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/members")
                .then().log().all()
                .extract();

        AuthRequest authRequest = new AuthRequest(USERNAME, PASSWORD);
        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(authRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AccessTokenResponse.class);

        return tokenResponse.getAccessToken();
    }
}
