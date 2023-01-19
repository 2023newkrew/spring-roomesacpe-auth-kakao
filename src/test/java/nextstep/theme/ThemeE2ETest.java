package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
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
public class ThemeE2ETest {
    private String accessTokenOfUserRole;
    private String accessTokenOfAdminRole;

    @BeforeEach
    void setUp() {
        MemberRequest userMember = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userMember)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        TokenRequest tokenRequestOfUser = new TokenRequest("username", "password");
        accessTokenOfUserRole = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequestOfUser)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value()).extract().as(TokenResponse.class).getAccessToken();

        TokenRequest tokenRequestOfAdmin = new TokenRequest("admin", "0000");
        accessTokenOfAdminRole = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequestOfAdmin)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value()).extract().as(TokenResponse.class).getAccessToken();
    }


    @DisplayName("관리자 권한으로 테마를 생성시 생성되어야 한다")
    @Test
    public void createWithAdmin() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessTokenOfAdminRole)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("사용자 권한으로 테마를 생성시 예외처리 되어야 한다")
    @Test
    public void createWithUser() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessTokenOfUserRole)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 권한으로 조회시 조회 가능해야 한다")
    @Test
    public void showThemesWithAdmin() {
        createTheme();

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .auth().oauth2(accessTokenOfAdminRole)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("사용자 권한으로 조회시 예외처리 되어야 한다")
    @Test
    public void showThemesWithUser() {
        createTheme();

        RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .auth().oauth2(accessTokenOfUserRole)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 권한으로 삭제시 삭제되어야 한다")
    @Test
    void deleteWithAdmin() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessTokenOfAdminRole)
                .when().delete("/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("사용자 권한으로 삭제시 예외처리 되어야 한다")
    @Test
    void deleteWithUser() {
        Long id = createTheme();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessTokenOfAdminRole)
                .when().delete("/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    public Long createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
