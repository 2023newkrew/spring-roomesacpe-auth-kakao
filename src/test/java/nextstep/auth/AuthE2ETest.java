package nextstep.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import nextstep.dto.request.MemberRequest;
import nextstep.dto.request.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.common.fixture.MemberProvider.로그인을_한다;
import static nextstep.common.fixture.MemberProvider.멤버를_생성한다;
import static nextstep.common.fixture.ThemeProvider.테마를_생성한다;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @BeforeEach
    void setUp() {
        멤버를_생성한다(new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678"));
    }

    @DisplayName("토큰을 생성한다")
    @Test
    public void create() {
        TokenResponse response = 로그인을_한다(new TokenRequest(USERNAME, PASSWORD));

        assertThat(response).isNotNull();
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 22000));

        var response = RestAssured
                .given().log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("일반 사용자는 테마를 삭제할 수 없다.")
    @Test
    void delete() {
        TokenResponse memberTokenResponse = 로그인을_한다(new TokenRequest(USERNAME, PASSWORD));
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        ExtractableResponse<Response> themeResponse = 테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 22000));

        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + memberTokenResponse.getAccessToken())
                .when().delete("/admin" + themeResponse.header(HttpHeaders.LOCATION))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

}
