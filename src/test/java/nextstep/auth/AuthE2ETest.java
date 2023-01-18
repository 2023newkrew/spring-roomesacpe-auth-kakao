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

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private Long memberId;

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void create() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);

        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @DisplayName("토큰 생성에 실패한다 - 비밀번호 불일치, 401 코드 반환")
    @Test
    void createToken_wrongPassword() {
        TokenRequest body = new TokenRequest(USERNAME, "wrongPassword");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(ErrorCode.UNAUTHORIZED.getStatus())
                .body("code", is(ErrorCode.UNAUTHORIZED.getCode()));
    }

    @DisplayName("토큰 생성에 실패한다 - 유저네임에 해당하는 멤버 없음, 404 코드 반환")
    @Test
    public void createTokenFailure() {
        TokenRequest body = new TokenRequest("notexistname", "wrongPassword");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(ErrorCode.MEMBER_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.MEMBER_NOT_FOUND.getCode()));
    }
}
