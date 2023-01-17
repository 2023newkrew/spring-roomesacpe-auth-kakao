package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

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

    @DisplayName("자기 자신의 정보를 조회")
    @Test
    public void findMyInfo() {
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

    @DisplayName("자기 자신의 정보를 조회 - 잘못된 토큰인 경우 400 코드 반환")
    @Test
    void me_wrongToken() {
        createMember();

        String userName = "username";
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "wrongToken")
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("자기 자신의 정보를 조회 - 회원정보가 존재하지 않으면 400 코드 반환")
    @Test
    void me_empty() {
        String userName = "username";
        String token = jwtTokenProvider.createToken(userName);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "wrongToken")
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
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
