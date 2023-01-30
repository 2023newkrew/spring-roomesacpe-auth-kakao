package nextstep.theme;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.auth.E2ETestAuthUtils;
import nextstep.member.E2ETestMemberUtils;
import nextstep.theme.dto.request.ThemeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeAdminE2ETest {

    public static final String THEME_NAME = "테마이름";
    public static final String THEME_DESC = "테마설명";
    public static final Integer THEME_PRICE = 22000;

    @DisplayName("관리자 권한으로 테마를 생성한다.")
    @Test
    public void create() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("일반 유저 권한으로 테마를 생성할 수 없다.")
    @Test
    public void createWithUserAuthority() {
        E2ETestMemberUtils.createMember();
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        String accessToken = E2ETestAuthUtils.loginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그인을 하지 않으면 테마를 생성할 수 없다.")
    @Test
    public void createWithNoneAuthority() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자 권한으로 테마를 삭제한다")
    @Test
    void delete() {
        Long generatedId = E2ETestThemeUtils.createTheme();
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/themes/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    @DisplayName("일반 유저 권한으로 테마를 삭제할 수 없다.")
    @Test
    void deleteWithUserAuthority() {
        E2ETestMemberUtils.createMember();
        Long generatedId = E2ETestThemeUtils.createTheme();
        String accessToken = E2ETestAuthUtils.loginAndGetAccessToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/admin/themes/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @DisplayName("로그인을 하지 않으면 테마를 삭제할 수 없다.")
    @Test
    void deleteWithNoneAuthority() {
        Long generatedId = E2ETestThemeUtils.createTheme();

        RestAssured
                .given().log().all()
                .when().delete("/admin/themes/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("관리자 권한으로 테마 목록을 조회한다 (일반 api 테스트)")
    @Test
    public void showThemes() {
        E2ETestThemeUtils.createTheme();
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .param("date", "2022-08-11")
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }
}
