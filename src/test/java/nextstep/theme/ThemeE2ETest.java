package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberRequest;
import nextstep.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeE2ETest {

    public static final String USERNAME = "username";
    public static final String ADMIN = "admin";
    public static final String USER_TOKEN = new JwtTokenProvider().createToken(USERNAME);
    public static final String ADMIN_TOKEN = new JwtTokenProvider().createToken(ADMIN);

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        createMember();
        createTheme();
        var response = RestAssured
                .given().header("authorization", "Bearer " + USER_TOKEN).log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    public void createTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().header("authorization", "Bearer " + ADMIN_TOKEN).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all();
    }

    public void createMember() {
        MemberRequest memberRequest = new MemberRequest(USERNAME, "password", "name", "010-1234-5678", MemberRole.USER);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all();
    }

}
