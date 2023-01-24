package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ThemeE2ETest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    String token;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.createToken("admin1");
    }

    @DisplayName("테마를 생성한다")
    @Test
    void create() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    void showThemes() {
        createTheme();

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".")).hasSize(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void delete() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("존재하지 않는 테마를 삭제하면 404 코드 반환")
    @Test
    void delete_fail() {
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/admin/themes/" + -1L)
                .then().log().all()
                .statusCode(ErrorCode.THEME_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.THEME_NOT_FOUND.getCode()));
    }

    private Long createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        return Long.parseLong(location.split("/")[2]);
    }
}
