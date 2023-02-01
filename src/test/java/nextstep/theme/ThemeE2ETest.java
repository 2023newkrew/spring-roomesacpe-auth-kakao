package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.dto.theme.ThemeRequest;
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

    @DisplayName("테마를 생성한다")
    @Test
    public void create() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
                .when().get(BASE_THEME_URI)
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
                .body(body)
                .when().post(BASE_THEME_URI)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[3]);
    }
}
