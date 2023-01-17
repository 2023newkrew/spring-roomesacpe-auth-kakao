package nextstep.theme;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import nextstep.auth.AuthUtil;
import nextstep.auth.TokenResponse;
import nextstep.member.Member;
import nextstep.member.MemberUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ThemeE2ETest {

    public static final ThemeRequest DEFAULT_THEME_REQUEST = new ThemeRequest("테마이름", "테마설명", 22000);

    @DisplayName("인증된 사용자는 테마를 생성할 수 있다.")
    @Test
    void test1() {
        Member ReservationExistUser = MemberUtil.getReservationExistMember(1L);
        TokenResponse tokenResponse = AuthUtil.createToken(ReservationExistUser);

        createThemeAndGetValidatableResponse(DEFAULT_THEME_REQUEST, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("인증되지 않은 사용자는 테마를 생성할 수 없다.")
    @Test
    void test2() {
        createThemeAndGetValidatableResponse(DEFAULT_THEME_REQUEST, "")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("인증 유무와 관계 없이 테마 목록을 조회할 수 았다.")
    @Test
    void test3() {
        List<Theme> themes = getThemes("2022-11-11");
        assertThat(themes).hasSize(2);
    }

    @DisplayName("인증된 사용자는 테마를 삭제할 수 있다.")
    @Test
    void test4() {
        Member ReservationExistUser = MemberUtil.getReservationExistMember(1L);
        TokenResponse tokenResponse = AuthUtil.createToken(ReservationExistUser);

        deleteThemeAndGetValidatableResponse(1L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("인증되지 않은 사용자는 테마를 삭제할 수 없다.")
    @Test
    void test5() {
        deleteThemeAndGetValidatableResponse(1L, "")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private static List<Theme> getThemes(String date) {
        return requestThemesAndGetValidatableResponse(date)
                .extract()
                .jsonPath()
                .getList(".", Theme.class);
    }

    private static ValidatableResponse requestThemesAndGetValidatableResponse(String date) {
        return RestAssured
                .given().log().all()
                .param("date", date)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private static ValidatableResponse deleteThemeAndGetValidatableResponse(Long id, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/themes/" + id)
                .then().log().all();
    }

    private static ValidatableResponse createThemeAndGetValidatableResponse(ThemeRequest themeRequest, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all();
    }
}
