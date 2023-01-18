package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MemberE2ETest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("멤버를 생성한다")
    @Test
    void create() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678", "user");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("자신의 토큰의 id 해당하는 멤버를 조회한다")
    @Test
    void me() {
        createMember();

        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(jwtTokenProvider.createToken("1", List.of("role")))
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        Assertions.assertThat(response.as(Member.class)).isEqualTo(
                new Member(1L, "username", "password", "name", "010-1234-5678", Role.USER)
        );
    }

    @DisplayName("토큰 없이 me를 요청하면 401 statusCode를 반환한다")
    @Test
    void meWithoutToken() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void createMember() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678", "user");
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then();
    }
}
