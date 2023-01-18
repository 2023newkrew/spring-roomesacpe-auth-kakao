package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.dto.request.ThemeRequest;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
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
public class AdminE2ETest {
    private static final long ADMIN_MEMBER_ID = 1L;
    private static final String ADMIN_PASSWORD = "admin";

    private String adminAccessToken;

    @BeforeEach
    void setUp() {
        TokenRequest tokenRequest = new TokenRequest(ADMIN_MEMBER_ID, ADMIN_PASSWORD);
        this.adminAccessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().response().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("관리자는 테마를 생성할 수 있다")
    @Test
    public void createByAdmin() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .header("Authorization", this.adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("관리자 외에는 테마를 생성할 수 없다")
    @Test
    public void createByUser() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("관리자는 테마를 삭제할 수 있다")
    @Test
    void deleteByAdmin() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .header("Authorization", this.adminAccessToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("관리자 외에는 테마를 삭제할 수 없다")
    @Test
    void deleteByUser() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public Long createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .header("Authorization", this.adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
