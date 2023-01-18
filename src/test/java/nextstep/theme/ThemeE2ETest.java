package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.domain.model.request.ThemeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static nextstep.auth.LoginUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ThemeE2ETest {
    private String token;

    @Test
    @DisplayName("유저는 테마를 생성할 수 없다.")
    public void createByUser() {
        token = loginUser();
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/themes/admin")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("관리자는 테마를 생성할 수 있다.")
    public void createByAdmin() {
        token = loginAdmin();
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(token)
                .when().post("/themes/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("테마 목록을 조회한다")
    public void showThemes() {
        token = loginUser();

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .auth().oauth2(token)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저는 테마를 삭제할 수 없다.")
    void deleteByUser() {
        token = loginUser();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/themes/admin/" + 9999)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("관리자는 테마를 삭제할 수 있다.")
    void deleteByAdmin() {
        token = loginAdmin();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/themes/admin/" + 9999)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
