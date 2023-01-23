package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @DisplayName("id/pw를 통해 토큰을 생성후 반환한다.")
    @Test
    public void create() {
        //given
        createMember(USERNAME, PASSWORD);
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);

        //when
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        //then
        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @Test
    @DisplayName("등록되지 않은 멤버로 토큰 생성을 요청하면 실패한다.")
    void createTokenFail() {
        //given
        TokenRequest tokenRequest = new TokenRequest("???", "????");

        //when
        //then
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("잘못된 password가 주어질 때 토큰 생성에 실패한다.")
    void createTokenFail_with_InvalidPassword() {
        //given
        createMember(USERNAME, PASSWORD);
        TokenRequest tokenRequest = new TokenRequest(USERNAME, "invalid");

        //when
        //then
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void createMember(String username, String password) {
        MemberRequest memberRequest = new MemberRequest(username, password, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
