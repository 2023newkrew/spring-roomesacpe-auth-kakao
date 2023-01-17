package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        String userName = "username";
        MemberRequest memberRequest = new MemberRequest(userName, "password", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

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
}
