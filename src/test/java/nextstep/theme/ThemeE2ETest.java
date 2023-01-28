package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.AuthorizationTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberRequest;
import nextstep.reservation.ReservationRequest;
import nextstep.schedule.ScheduleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String adminToken;
    private String normalToken;
    private String invalidToken;
    private static final String NORMAL_MEMBER_NAME = "normal_member";
    private static final String ADMIN_MEMBER_NAME = "admin_member";

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(NORMAL_MEMBER_NAME, "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        adminToken = AuthorizationTokenExtractor.BEARER_TYPE + " " + jwtTokenProvider.createToken(ADMIN_MEMBER_NAME);
        normalToken = AuthorizationTokenExtractor.BEARER_TYPE + " " + jwtTokenProvider.createToken(NORMAL_MEMBER_NAME);
        invalidToken = "foo";
    }

    @DisplayName("어드민이 테마를 생성하면 201 코드 응답")
    @Test
    public void createThemeByAdminMember() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .header(AuthorizationTokenExtractor.AUTHORIZATION, adminToken)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("어드민이 아닌 멤버가 테마를 생성하면 403 코드 응답")
    @Test
    public void createThemeByNormalMember() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .header(AuthorizationTokenExtractor.AUTHORIZATION, normalToken)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그인 하지 않은 멤버가 테마를 생성하면 401 코드 응답")
    @Test
    public void createThemeByUnknown() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .header(AuthorizationTokenExtractor.AUTHORIZATION, invalidToken)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        createTheme(adminToken);

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("어드민이 테마를 삭제하면 204 코드 응답")
    @Test
    void deleteThemeByAdminMember() {
        Long id = createTheme(adminToken);

        RestAssured
                .given().log().all()
                .header(AuthorizationTokenExtractor.AUTHORIZATION, adminToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("어드민이 아닌 멤버가 테마를 삭제하면 403 코드 응답")
    @Test
    void deleteThemeByNormalMember() {
        Long id = createTheme(adminToken);

        RestAssured
                .given().log().all()
                .header(AuthorizationTokenExtractor.AUTHORIZATION, normalToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그인 하지 않은 멤버가 테마를 삭제하면 401 코드 응답")
    @Test
    void deleteThemeByUnknown() {
        Long id = createTheme(adminToken);

        RestAssured
                .given().log().all()
                .header(AuthorizationTokenExtractor.AUTHORIZATION, invalidToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    public Long createTheme(String token) {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .header(AuthorizationTokenExtractor.AUTHORIZATION, token)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
