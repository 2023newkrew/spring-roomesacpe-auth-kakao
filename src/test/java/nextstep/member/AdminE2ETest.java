package nextstep.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.ScheduleRequest;
import nextstep.dto.request.ThemeRequest;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static nextstep.common.fixture.MemberProvider.로그인을_한다;
import static nextstep.common.fixture.ScheduleProvider.스케줄을_생성한다;
import static nextstep.common.fixture.ThemeProvider.테마를_생성한다;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminE2ETest {

    @Test
    void 관리자는_테마를_생성할_수_있다() {
        // given
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 20000);

        // when
        ExtractableResponse<Response> response = 테마를_생성한다(adminTokenResponse.getAccessToken(), themeRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 관리자는_테마를_삭제할_수_있다() {
        // given
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        ExtractableResponse<Response> response = 테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 20000));

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminTokenResponse.getAccessToken())

        // when
        .when()
                .delete("/admin" + response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 관리자는_스케줄을_생성할_수_있다() {
        // given
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 20000));
        ScheduleRequest scheduleRequest = new ScheduleRequest(1L, "2023-01-18", "13:00");

        // when
        ExtractableResponse<Response> response = 스케줄을_생성한다(adminTokenResponse.getAccessToken(), scheduleRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 관리자는_스케줄을_삭제할_수_있다() {
        // given
        TokenResponse adminTokenResponse = 로그인을_한다(new TokenRequest("admin", "admin"));
        테마를_생성한다(adminTokenResponse.getAccessToken(), new ThemeRequest("테마이름", "테마설명", 20000));
        ExtractableResponse<Response> response = 스케줄을_생성한다(adminTokenResponse.getAccessToken(), new ScheduleRequest(1L, "2023-01-18", "13:00"));

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminTokenResponse.getAccessToken())

        // when
        .when()
                .delete("/admin" + response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
