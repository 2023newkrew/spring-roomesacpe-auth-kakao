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
import static nextstep.Constant.USERNAME;
import static nextstep.Constant.PASSWORD;
import static nextstep.Constant.NAME;
import static nextstep.Constant.PHONE;
import static nextstep.Constant.AUTHORIZATION;
import static nextstep.Constant.BEARER_TYPE;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MemberE2ETest {
    @DisplayName("멤버를 생성한다")
    @Test
    void create() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value())
                .header("Location", "/members/1");
    }

    @DisplayName("토큰 정보로 회원 정보를 조회한다")
    @Test
    void findMemberByToken() {
        create();

        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        String accessToken = getToken(body);

        MemberResponse memberResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when().get("/members/me")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        assertThat(memberResponse.getUsername()).isEqualTo(USERNAME);
        assertThat(memberResponse.getPassword()).isEqualTo(PASSWORD);
        assertThat(memberResponse.getName()).isEqualTo(NAME);
        assertThat(memberResponse.getPhone()).isEqualTo(PHONE);
    }

    private String getToken(TokenRequest tokenRequest) {
        return RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();
    }
}
