package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MemberE2ETest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("멤버를 생성한다")
    @Test
    void create() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("자기 자신의 정보를 조회")
    @Test
    void findMyInfo() {
        createMember();

        String userName = "username";
        String token = jwtTokenProvider.createToken(userName);

        Member member = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(Member.class);

        assertThat(member.getUsername()).isEqualTo(userName);
    }

    @DisplayName("자기 자신의 정보를 조회 - 유효하지 않은 토큰인 경우 401 코드 반환")
    @Test
    void me_wrongToken() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "wrongToken")
                .when().get("/members/me")
                .then().log().all()
                .statusCode(ErrorCode.INVALID_TOKEN.getStatus())
                .body("code", is(ErrorCode.INVALID_TOKEN.getCode()));
    }

    @DisplayName("자기 자신의 정보를 조회 - username에 대한 멤버를 찾을 수 없는 경우 404 코드 반환")
    @Test
    void me_notFound() {
        String userName = "username";
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", AuthorizationTokenExtractor.BEARER_TYPE + " " + token)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(ErrorCode.USER_NOT_FOUND.getStatus())
                .body("code", is(ErrorCode.USER_NOT_FOUND.getCode()));
    }

    private void createMember() {
        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
