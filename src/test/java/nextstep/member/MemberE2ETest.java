package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberE2ETest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("내 정보를 조회")
    @Test
    public void meTest() {

        String username = "username";
        // 멤버 등록
        MemberRequest memberRequest = new MemberRequest(username, "password", "name", "010-1234-5678");
        memberService.create(memberRequest);

        // token 가져오기
        String token = jwtTokenProvider.createToken(username);

        // authorizon 헤더 만들기
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + token)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(2))
                .body("username", equalTo("username"))
                .body("password", equalTo("password"))
                .body("name", equalTo("name"))
                .body("phone", equalTo("010-1234-5678"))
                .body("role", equalTo("USER"));
    }
}
