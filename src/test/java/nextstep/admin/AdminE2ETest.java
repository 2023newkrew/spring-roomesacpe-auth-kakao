package nextstep.admin;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
import nextstep.theme.dto.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminE2ETest {
    private String accessToken;
    private String adminToken;

    @BeforeEach
    void setUp() {
        MemberRequest request = new MemberRequest("sienna-o", "password", "subin", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .body(request)
                .post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        TokenRequest tokenRequest = new TokenRequest(request.getUsername(), request.getPassword());
        accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .body(tokenRequest)
                .post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();

        MemberRequest adminRequest = new MemberRequest("admin", "password", "admin", "010-8712-3452");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .body(adminRequest)
                .post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        TokenRequest adminTokenRequest = new TokenRequest(request.getUsername(), request.getPassword());
        String beforeAdminToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .body(adminTokenRequest)
                .post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();

        adminToken = RestAssured
                .given().log().all()
                .auth().oauth2(beforeAdminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("로그인 된 사용자는 자신을 관리자로 추가할 수 있다")
    @Test
    void registerAdmin() {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("로그인하지 않은 사용자는 관리자를 추가할 수 없다")
    @Test
    void registerAdminByUnAuthorizedMember() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인 된 사용자는 자신을 관리자에서 해지할 수 있다")
    @Test
    void deregisterAdmin() {
        RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .delete("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
    @DisplayName("관리자는 사용자 목록을 조회할 수 있다")
    @Test
    void getMembers() {
        List<MemberResponse> members = List.of(
                RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .get("/admin/members")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse[].class)
        );

        assertThat(members.size()).isEqualTo(2);
    }

    @DisplayName("사용자는 사용자 목록을 조회할 수 없다")
    @Test
    void getMembersByMember() {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .get("/admin/members")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("관리자는 테마를 생성할 수 있다")
    @Test
    void createTheme() {
        ThemeRequest request = new ThemeRequest("테마 이름", "설명", 23010);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        assertThat(response.header("location")).isNotNull();
    }

    @DisplayName("사용자는 테마를 생성할 수 없다")
    @Test
    void createThemeByMember() {
        ThemeRequest request = new ThemeRequest("테마 이름", "설명", 23010);

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("관리자는 테마를 삭제할 수 있다")
    @Test
    void deleteTheme() {
        ThemeRequest request = new ThemeRequest("테마 이름", "설명", 23010);
        String location = RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("location");
        Long themeId = Long.parseLong(location.split("/")[2]);

        RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .delete("/admin/themes/" + themeId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("사용자는 테마를 삭제할 수 없다")
    @Test
    void deleteThemeByMember() {
        ThemeRequest request = new ThemeRequest("테마 이름", "설명", 23010);
        String location = RestAssured
                .given().log().all()
                .auth().oauth2(adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("location");

        Long themeId = Long.parseLong(location.split("/")[2]);

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .delete("/admin/themes/" + themeId)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}