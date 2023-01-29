package nextstep.member;

import static org.hamcrest.core.Is.is;

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
import org.springframework.test.context.jdbc.Sql;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/init.sql")
public class MemberE2ETest {

    private MemberCreateRequest memberCreateRequest;

    @BeforeEach
    void setUp() {
        memberCreateRequest = new MemberCreateRequest("username", "password", "name", "010-1234-5678");
    }

    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberCreateRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void show() {
        createMemberAndGetId();

        TokenRequest tokenRequest = new TokenRequest("username", "password");
        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);

        String accessToken = tokenResponse.getAccessToken();
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("username", is("username"))
                .body("name", is("name"))
                .body("phone", is("010-1234-5678"));
    }

    @DisplayName("사용자에게 관리자 권한 부여 테스트")
    @Test
    void authorization() {
        MemberCreateRequest adminBody = new MemberCreateRequest("admin", "admin", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(adminBody)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        TokenRequest adminTokenRequest = new TokenRequest("admin", "admin");
        var adminTokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(adminTokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);
        String adminAccessToken = adminTokenResponse.getAccessToken();

        Long id = createMemberAndGetId();

        MemberAuthorizationRequest memberAuthorizationRequest = new MemberAuthorizationRequest(id);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(adminAccessToken)
                .body(memberAuthorizationRequest)
                .when().post("/admin/authorization")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("username", is("username"))
                .body("name", is("name"))
                .body("phone", is("010-1234-5678"))
                .body("role", is(MemberRole.ADMIN.name()));
    }

    private Long createMemberAndGetId() {
        var response = RestAssured
                .given().log().all()
                .body(memberCreateRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/members")
                .then().log().all()
                .extract();

        String[] location = response.header("Location").split("/");
        return Long.parseLong(location[location.length - 1]);
    }
}
