package nextstep.member;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import nextstep.auth.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class MemberAdminE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    @DisplayName("어드민 권한으로 전체 멤버를 조회한다.")
    @Test
    public void showAllMembers() {
        createMember();

        String accessToken = adminLoginAndGetAccessToken();
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/admin/members")
                .then().log().all()
                .extract();

        List<Member> members = response.jsonPath().getList(".", Member.class);
        assertThat(members.size()).isEqualTo(2);
    }

    @DisplayName("일반 유저 권한으로는 전체 멤버를 조회할 수 없다.")
    @Test
    public void showAllMembersWithUserAuthority() {
        createMember();

        String accessToken = loginAndGetAccessToken();
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/admin/members")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @DisplayName("로그인을 하지 않고 전체 멤버를 조회할 수 없다.")
    @Test
    public void showAllMembersWithNoneAuthority() {
        createMember();

        RestAssured
                .given().log().all()
                .when().get("/admin/members")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("관리자 권한으로 내 정보를 조회할 수 있다.(일반 api 테스트)")
    @Test
    public void getMyInfoWithAdminAuthority() {
        String accessToken = adminLoginAndGetAccessToken();
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(Member.class)).isNotNull();
    }

    public Long createMember() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678");
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }

    private String loginAndGetAccessToken() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return accessToken.toString();
    }

    private String adminLoginAndGetAccessToken() {
        TokenRequest body = new TokenRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return accessToken.toString();
    }
}
