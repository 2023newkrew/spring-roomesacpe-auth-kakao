package nextstep.auth;

import io.restassured.RestAssured;
import nextstep.error.ErrorCode;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthE2ETest {
    private static final String MEMBER_USERNAME = "username";
    private static final String MEMBER_PASSWORD = "password";

    private static final String ADMIN_USERNAME = "admin1";
    private static final String ADMIN_PASSWORD = "admin1";

    private Long memberId;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(MEMBER_USERNAME, MEMBER_PASSWORD, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("일반 사용자가 로그인을 요청해서 로그인에 성공하면 토큰을 반환한다")
    @Test
    void loginMember() {
        TokenRequest body = new TokenRequest(MEMBER_USERNAME, MEMBER_PASSWORD);

        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/member")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @DisplayName("일반 사용자의 토큰 생성에 실패한다 - 비밀번호 불일치, 401 코드 반환")
    @Test
    void loginMember_wrongPassword() {
        TokenRequest body = new TokenRequest(MEMBER_USERNAME, "wrongPassword");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/member")
                .then().log().all()
                .statusCode(ErrorCode.UNAUTHORIZED.getStatus())
                .body("code", is(ErrorCode.UNAUTHORIZED.getCode()));
    }

    @DisplayName("일반 사용자의 토큰 생성에 실패한다 - 유저네임에 해당하는 Member 없음, 404 코드 반환")
    @Test
    void loginMember_notFound() {
        TokenRequest body = new TokenRequest("notexistname", "wrongPassword");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/member")
                .then().log().all()
                .statusCode(ErrorCode.USER_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.USER_NOT_FOUND.getCode()));
    }

    @DisplayName("관리자가 로그인을 요청해서 로그인에 성공하면 토큰을 반환한다")
    @Test
    void loginAdmin() {
        TokenRequest body = new TokenRequest(ADMIN_USERNAME, ADMIN_PASSWORD);

        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/member")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @DisplayName("관리자의 토큰 생성에 실패한다 - 비밀번호 불일치, 401 코드 반환")
    @Test
    void loginAdmin_wrongPassword() {
        TokenRequest body = new TokenRequest(ADMIN_USERNAME, "wrongPassword");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/member")
                .then().log().all()
                .statusCode(ErrorCode.UNAUTHORIZED.getStatus())
                .body("code", is(ErrorCode.UNAUTHORIZED.getCode()));
    }

    @DisplayName("관리자의 토큰 생성에 실패한다 - 유저네임에 해당하는 Admin 없음, 404 코드 반환")
    @Test
    void loginAdmin_notFound() {
        TokenRequest body = new TokenRequest("notexistname", ADMIN_PASSWORD);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/member")
                .then().log().all()
                .statusCode(ErrorCode.USER_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.USER_NOT_FOUND.getCode()));
    }

}
