package nextstep.member;

import io.restassured.RestAssured;
import nextstep.interfaces.auth.dto.TokenRequest;
import nextstep.interfaces.auth.dto.TokenResponse;
import nextstep.domain.member.Member;
import nextstep.interfaces.member.dto.MemberRequest;
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
    public static final String PASSWORD = "password";


    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("자기 정보를 조회한다.")
    @Test
    public void showMe(){
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all();

        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        Member member = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(Member.class);

        assertThat(member.getUsername().equals(USERNAME)).isTrue();
    }
//
//    @DisplayName("테마 목록을 조회한다")
//    @Test
//    public void showThemes() {
//        createTheme();
//
//        var response = RestAssured
//                .given().log().all()
//                .param("date", "2022-08-11")
//                .when().get("/themes")
//                .then().log().all()
//                .statusCode(HttpStatus.OK.value())
//                .extract();
//        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
//    }
//
//    @DisplayName("테마를 삭제한다")
//    @Test
//    void delete() {
//        Long id = createTheme();
//
//        var response = RestAssured
//                .given().log().all()
//                .when().delete("/themes/" + id)
//                .then().log().all()
//                .extract();
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//
//    public Long createTheme() {
//        ThemeRequest body = new ThemeRequest("테마이름", "테마설명", 22000);
//        String location = RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(body)
//                .when().post("/themes")
//                .then().log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract().header("Location");
//        return Long.parseLong(location.split("/")[2]);
//    }
}
