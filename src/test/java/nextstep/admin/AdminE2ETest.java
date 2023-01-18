package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.dto.MemberCreatedResponse;
import nextstep.member.dto.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminE2ETest {
    private String accessToken;
    private Long memberId;

    @BeforeEach
    void setUp() {
        MemberRequest request = new MemberRequest("sienna-o", "password", "subin", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .body(request)
                .post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        TokenRequest tokenRequest = new TokenRequest(request.getUsername(), request.getPassword());
        accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .body(tokenRequest)
                .post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();

        memberId = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().log().all()
                .get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberCreatedResponse.class).getId();
    }

    @DisplayName("사용자를 관리자로 등록할 수 있다")
    @Test
    void registerAdmin() {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/admin/" + memberId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("관리자는 관리자를 해지할 수 있다")
    @Test
    void removeAdmin() {
        fail();
    }

    @DisplayName("사용자는 관리자를 해지할 수 없다")
    @Test
    void removeAdminByMember() {
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/admin/" + memberId)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
        fail();
    }

    @DisplayName("관리자는 사용자 목록을 조회할 수 있다")
    @Test
    void getMembers() {
        fail();
    }

    @DisplayName("사용자는 사용자 목록을 조회할 수 없다")
    @Test
    void getMembersByMember() {
        fail();
    }

    @DisplayName("관리자는 테마를 생성할 수 있다")
    @Test
    void createTheme() {
        fail();
    }

    @DisplayName("사용자는 테마를 생성할 수 없다")
    @Test
    void createThemeByMember() {
        fail();
    }

    @DisplayName("관리자는 테마를 삭제할 수 있다")
    @Test
    void deleteTheme() {
        fail();
    }

    @DisplayName("사용자는 테마를 삭제할 수 없다")
    @Test
    void deleteThemeByMember() {
        fail();
    }
}