package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NAME = "jay";
    private static final String PHONE = "010-1234-5678";

    @DisplayName("중복이 없는 username 과 phone 으로 멤버 생성시 생성되어야 한다")
    @Test
    public void createNormally() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("중복된 username 이나 phone 으로 맴버 생성시 예외처리 되어야 한다")
    @ParameterizedTest
    @CsvSource(value = {
            "username;010-1234-1234",
            "user;010-1234-5678"
    }, delimiter = ';')
    public void createByDuplicateUsernameOrPhone(String username, String phone) {
        MemberRequest oldMember = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(oldMember)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        MemberRequest newMember = new MemberRequest(username, PASSWORD, NAME, phone);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newMember)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("발급된 토큰으로 유저 정보 열람 요청시 유저 정보가 전달되어야 한다")
    @Test
    public void readMyDataNormally() {
        MemberRequest memberRequest = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(memberRequest)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value());

        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);
        String accessToken = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value()).extract().as(TokenResponse.class).getAccessToken();

        Member member = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(Member.class);

        assertThat(member.getUsername()).isEqualTo(USERNAME);
    }

    @DisplayName("토큰이 없이 유저 정보 열람 요청시 예외처리 되어야 한다")
    @Test
    public void readMyDataWithoutToken() {
        RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
