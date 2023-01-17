package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.AuthUtil;
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
    @DisplayName("멤버를 생성한다")
    @Test
    void create() {
        MemberRequest memberRequest = new MemberRequest("username2", "password", "name", "010-1234-5678");
        createMember(memberRequest);
    }

    @DisplayName("내 정보를 조회한다")
    @Test
    void me() {
        TokenRequest tokenRequest = AuthUtil.RESERVATION_EXIST_USER_TOKEN_REQUEST;
        final TokenResponse tokenResponse = AuthUtil.createToken(tokenRequest);
        final String accessToken = tokenResponse.getAccessToken();

        Member member = getMemberSelfInfo(accessToken);
        assertThat(member.getUsername()).isEqualTo(tokenRequest.getUsername());
    }

    private static void createMember(MemberRequest memberRequest) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    private static Member getMemberSelfInfo(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(Member.class);
    }
}
