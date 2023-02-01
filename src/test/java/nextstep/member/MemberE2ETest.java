package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.dto.TokenRequest;
import nextstep.member.domain.Member;
import nextstep.member.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String PHONE = "010-1234-5678";
    public static final String WRONG_PHONE = "111-121-444";

    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("empty string인 username으로 멤버를 생성할 수 없다")
    @Test
    public void createWithEmptyUsername() {
        MemberRequest body = new MemberRequest("", PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("null인 username으로 멤버를 생성할 수 없다")
    @Test
    public void createWithNullUsername() {
        MemberRequest body = new MemberRequest(null, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("blank로만 구성된 username으로 멤버를 생성할 수 없다")
    @Test
    public void createWithBlankUsername() {
        MemberRequest body = new MemberRequest("     ", PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("empty string인 password로 멤버를 생성할 수 없다")
    @Test
    public void createWithEmptyPassword() {
        MemberRequest body = new MemberRequest(USERNAME, "", NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("null인 password로 멤버를 생성할 수 없다")
    @Test
    public void createWithNullPassword() {
        MemberRequest body = new MemberRequest(USERNAME, null, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("blank로만 구성된 password로 멤버를 생성할 수 없다")
    @Test
    public void createWithBlankPassword() {
        MemberRequest body = new MemberRequest(USERNAME, "     ", NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("empty string인 name로 멤버를 생성할 수 없다")
    @Test
    public void createWithEmptyName() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "", PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("null인 name로 멤버를 생성할 수 없다")
    @Test
    public void createWithNullName() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, null, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("blank로만 구성된 name로 멤버를 생성할 수 없다")
    @Test
    public void createWithBlankName() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "     ", PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("잘못된 전화번호로 멤버를 생성할 수 없다")
    @Test
    public void createWithWrongPHONE() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, WRONG_PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("나 자신을 조회한다.")
    @Test
    public void showMe() {
        createMember();

        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");
        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(Member.class)).isNotNull();
    }

    public void createMember() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
    }
}
