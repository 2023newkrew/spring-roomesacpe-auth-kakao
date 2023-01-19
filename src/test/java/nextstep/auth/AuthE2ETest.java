package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.admin.AdminThemeRequest;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.auth.JwtTokenProvider.ACCESS_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthE2ETest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private long memberId;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 생성한다")
    @Test
    public void create() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();

        assertThat(response.cookie(ACCESS_TOKEN)).isNotNull();
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .getCookie(ACCESS_TOKEN);
        createTheme(accessToken);

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .cookie(ACCESS_TOKEN, accessToken)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void delete() {
        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .getCookie(ACCESS_TOKEN);
        long id = createTheme(accessToken);

        var response = RestAssured
                .given().log().all()
                .cookie(ACCESS_TOKEN, accessToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public long createTheme(String accessToken) {
        AdminThemeRequest body = new AdminThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .cookie(ACCESS_TOKEN, accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
