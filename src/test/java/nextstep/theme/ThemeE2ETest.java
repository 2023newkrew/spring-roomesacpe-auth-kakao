package nextstep.theme;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import nextstep.AbstractE2ETest;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

public class ThemeE2ETest extends AbstractE2ETest {
    @DisplayName("테마를 생성한다")
    @Test
    public void create() {
        String adminToken = createBearerToken("admin", "admin");
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        createTheme();

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
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
        String adminToken = createBearerToken("admin", "admin");

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest
    @MethodSource
    void createByInvalidFormatRequest(String name, String desc, int price) {
        String adminToken = createBearerToken("admin", "admin");
        ThemeRequest request = new ThemeRequest(name, desc, price);

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private static List<Arguments> createByInvalidFormatRequest() {
        return List.of(
                Arguments.of("n".repeat(21), "desc", 10_000),
                Arguments.of("name", "d".repeat(256), 10_000),
                Arguments.of("name", "desc", -1),
                Arguments.of("name", "desc", 100_001)
        );
    }

    public Long createTheme() {
        String adminToken = createBearerToken("admin", "admin");
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        String location = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
