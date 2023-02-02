package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.ChangeUserTypeRequest;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRequest;
import nextstep.type.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeE2ETest {

    Member adminMember;
    Member normalMember;
    String adminMemberToken;
    String normalMemberToken;

    @Autowired
    MemberDao memberDao;

    @Value("change-usertype-key")
    String secretKey;

    @DisplayName("테마를 생성한다")
    @Test
    public void create() {
        requestCreateAdminMember();

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .auth().oauth2(adminMemberToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("관리자가 아닌 멤버가 테마를 생성한다")
    @Test
    public void create_forbidden() {
        requestCreateMember();

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        RestAssured
                .given().log().all()
                .auth().oauth2(normalMemberToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("테마 목록을 조회한다")
    @Test
    public void showThemes() {
        requestCreateAdminMember();
        requestCreateTheme();

        var response = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void delete() {
        requestCreateAdminMember();
        Long id = requestCreateTheme();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(adminMemberToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("관리자가 아닌 멤버가 테마를 삭제한다")
    @Test
    void delete_forbidden() {
        requestCreateMember();
        requestCreateAdminMember();
        Long id = requestCreateTheme();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(normalMemberToken)
                .when().delete("/admin/themes/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    public Long requestCreateTheme() {
        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .auth().oauth2(adminMemberToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }

    private void requestCreateAdminMember() {
        // 멤버 생성
        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        // 멤버에게 관리자 권한 설정
        Long memberId = Long.parseLong(location.split("/")[2]);
        ChangeUserTypeRequest changeUserTypeRequest = new ChangeUserTypeRequest(memberId, UserType.ADMIN, secretKey);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changeUserTypeRequest)
                .when().patch("/members/type")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        // 관리자 멤버의 토큰 생성
        adminMember = memberDao.findById(memberId);
        TokenRequest tokenRequest = new TokenRequest(adminMember.getUsername(), adminMember.getPassword());

        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);

        adminMemberToken = tokenResponse.getAccessToken();
    }

    private void requestCreateMember() {
        // 멤버 생성
        MemberRequest memberRequest = new MemberRequest("username2", "password", "name2", "010-1234-5678");
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        Long memberId = Long.parseLong(location.split("/")[2]);
        normalMember = memberDao.findById(memberId);

        // 토큰 생성
        TokenRequest tokenRequest = new TokenRequest(normalMember.getUsername(), normalMember.getPassword());
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);
        normalMemberToken = tokenResponse.getAccessToken();
    }
}
