package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
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

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NAME = "jay";
    private static final String PHONE = "010-1234-5678";

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

    @DisplayName("멤버 정보 조회")
    @Test
    public void me() {
        // 멤버 생성
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value());

        // 토큰 발급
        TokenRequest request = new TokenRequest(USERNAME, PASSWORD);
        String accessToken = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(request)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value()).extract().as(TokenResponse.class).getAccessToken();

        // 토큰으로 멤버 정보 조회
        Member member = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(Member.class);

        assertThat(member.getUsername()).isEqualTo(USERNAME);
    }
}
