package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.dto.auth.TokenRequest;
import nextstep.dto.member.MemberRequest;
import nextstep.dto.theme.ThemeRequest;
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
    public static final String THEME_NAME = "테마이름";
    public static final String THEME_DESC = "테마설명";

    public static final int THEME_PRICE = 22000;
    public static final long WRONG_THEME_ID = 1000L;
    public static final int MINUS_PRICE = -1;
    public static final String BASE_THEME_URI = "/admin/themes";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";
    private static final String NORMAL_USERNAME = "username";
    private static final String NORMAL_PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE = "010-1234-5678";
    private String adminToken;
    private String normalToken;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(NORMAL_USERNAME, NORMAL_PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        adminToken = loginAndGetAccessToken(ADMIN_USERNAME, ADMIN_PASSWORD);
        normalToken = loginAndGetAccessToken(NORMAL_USERNAME,NORMAL_PASSWORD);
    }

    @DisplayName("테마를 생성한다")
    @Test
    public void create() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("음수인 가격으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithMinusPrice() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("null인 이름으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithNullName() {
        ThemeRequest body = new ThemeRequest(null, THEME_DESC, MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("empty string인 이름으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithEmptyName() {
        ThemeRequest body = new ThemeRequest("", THEME_DESC, MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("blank인 이름으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithBlankName() {
        ThemeRequest body = new ThemeRequest("      ", THEME_DESC, MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("null인 설명으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithNullDesc() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, null, MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("empty string인 설명으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithEmptyDesc() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, "", MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("blank인 설명으로 테마를 생성할 수 없다.")
    @Test
    public void cannotCreateThemeWithBlankDesc() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, "     ", MINUS_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        createTheme();

        var response = RestAssured
                .given().log().all()
                .header("authorization", adminToken)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void delete() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .header("authorization", adminToken)
                .when().delete(BASE_THEME_URI + "/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("존재하지 않는 테마를 삭제할 수 없다.")
    @Test
    void cannotDeleteThemeIfNotExists() {
        createTheme();

        var response = RestAssured
                .given().log().all()
                .header("authorization", adminToken)
                .when().delete(BASE_THEME_URI + "/" + WRONG_THEME_ID)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public Long createTheme() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", adminToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[3]);
    }

    @DisplayName("일반 사용자는 테마를 생성할 수 없다")
    @Test
    public void normalUserCannotCreateTheme() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", normalToken)
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("일반 사용자는 테마를 삭제할 수 없다")
    @Test
    void normalUserCannotDeleteTheme() {
        Long id = createTheme();

        RestAssured
                .given().log().all()
                .header("authorization", normalToken)
                .when().delete(BASE_THEME_URI + "/" + id)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("일반 사용자가 테마 목록을 조회한다")
    @Test
    public void showNormalUserToThemes() {
        createTheme();

        var response = RestAssured
                .given().log().all()
                .header("authorization", normalToken)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    private String loginAndGetAccessToken(String username, String password) {
        TokenRequest body = new TokenRequest(username, password);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");

        return "Bearer " + accessToken.toString();
    }
}
