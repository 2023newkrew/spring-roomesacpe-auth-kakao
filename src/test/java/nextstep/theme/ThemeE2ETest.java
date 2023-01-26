package nextstep.theme;

import static nextstep.Constant.ADMIN_PASSWORD;
import static nextstep.Constant.ADMIN_USERNAME;
import static nextstep.Constant.AUTHORIZATION;
import static nextstep.Constant.BEARER_TYPE;
import static nextstep.Constant.NAME;
import static nextstep.Constant.PASSWORD;
import static nextstep.Constant.PHONE;
import static nextstep.Constant.THEME_DESCRIPTION;
import static nextstep.Constant.THEME_NAME;
import static nextstep.Constant.THEME_PRICE;
import static nextstep.Constant.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ThemeE2ETest {
    private String adminToken;

    @BeforeEach
    void setUp() {
        TokenRequest tokenRequest = new TokenRequest(ADMIN_USERNAME, ADMIN_PASSWORD);

        TokenResponse tokenResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);

        adminToken = tokenResponse.getAccessToken();
    }

    @DisplayName("테마를 생성한다")
    @Test
    void createTheme() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER_TYPE + adminToken)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("로그인하지 않은 경우 테마를 생성할 수 없다")
    @Test
    void createThemeUnauthorized() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 계정이 아닌 경우 테마를 생성할 수 없다")
    @Test
    void createThemeForbidden() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        String otherToken = createToken();
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER_TYPE + otherToken)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    void showThemes() {
        getCreatedThemeId();

        var response = RestAssured
                .given().log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".")).hasSize(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void deleteTheme() {
        Long id = getCreatedThemeId();
        RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + adminToken)
                .when().delete("/admin/themes/" + id)
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("로그인하지 않은 경우 테마를 삭제할 수 없다")
    @Test
    void deleteThemeUnauthorized() {
        Long id = getCreatedThemeId();
        RestAssured
                .given().log().all()
                .when().delete("/admin/themes/" + id)
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 계정이 아닌 경우 테마를 생성할 수 없다")
    @Test
    void deleteThemeForbidden() {
        Long id = getCreatedThemeId();
        String otherToken = createToken();
        RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + otherToken)
                .when().delete("/admin/themes/" + id)
                .then().statusCode(HttpStatus.FORBIDDEN.value());
    }

    private Long getCreatedThemeId() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        String location = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER_TYPE + adminToken).body(themeRequest)
                .when().post("/admin/themes")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }

    private String createToken() {
        MemberRequest memberRequestBody = MemberRequest.builder()
                .username(USERNAME).password(PASSWORD).name(NAME).phone(PHONE).build();

        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(memberRequestBody)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value());

        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);

        TokenResponse tokenResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);

        return tokenResponse.getAccessToken();
    }
}
