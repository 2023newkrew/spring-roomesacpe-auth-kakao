package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberRole;
import nextstep.theme.ThemeRequest;
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

    public static final String USERNAME = "username";
    public static final String USER_TOKEN = new JwtTokenProvider().createToken(USERNAME, MemberRole.USER);
    public static final String ADMIN_TOKEN = new JwtTokenProvider().createToken(USERNAME, MemberRole.ADMIN);

    @DisplayName("관리자가 테마를 생성한다")
    @Test
    public void createThemeByAdmin() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().header("authorization", "Bearer " + ADMIN_TOKEN).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("일반 회원이 테마를 생성하지 못한다")
    @Test
    public void createThemeByUser() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().header("authorization", "Bearer " + USER_TOKEN).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("관리자가 테마를 삭제한다")
    @Test
    void deleteThemeByAdmin() {
        Long id = createTheme();

        var response = RestAssured
                .given().header("authorization", "Bearer " + ADMIN_TOKEN).log().all()
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("일반 회원이 테마를 삭제하지 못한다")
    @Test
    void deleteThemeByUser() {
        Long id = createTheme();

        RestAssured
                .given().header("authorization", "Bearer " + USER_TOKEN).log().all()
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    public Long createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().header("authorization", "Bearer " + ADMIN_TOKEN).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
