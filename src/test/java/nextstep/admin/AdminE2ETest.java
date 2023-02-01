package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.setup.TestSetUp;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminE2ETest {
    private Long memberId;
    private String adminToken;
    private String token;

    @BeforeEach
    void setUp() {
        memberId = TestSetUp.registerMember();

        adminToken = TestSetUp.getAdminToken();

        token = TestSetUp.getUserToken();
    }

    @DisplayName("관리자는 테마를 생성할 수 있다")
    @Test
    public void adminCanCreateTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("관리자가 아닌 유저는 테마를 생성할 수 없다")
    @Test
    public void userCantCreateTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("토큰 없이는 테마를 생성할 수 없다")
    @Test
    public void noHeaderCantCreateTheme() {
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
    void adminCanDeleteTheme() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("관리자가 아닌 유저는 테마를 삭제할 수 없다")
    @Test
    void userCantDeleteTheme() {
        Long id = createTheme();

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("토큰 없이는 테마를 삭제할 수 없다")
    @Test
    void noHeaderCantDeleteTheme() {
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
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
