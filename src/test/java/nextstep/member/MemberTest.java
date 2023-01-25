package nextstep.member;

import io.restassured.RestAssured;
import nextstep.AcceptanceTestExecutionListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class MemberTest {
    @DisplayName("멤버를 생성할 수 있다")
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

    @DisplayName("조회를 할 때 로그인이 되지 않았을 경우, 에러 발생")
    @Test
    void notLogin() {
        create();
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.LENGTH_REQUIRED.value());
    }

    @DisplayName("잘못된 토큰을 입력하는 경우, 에러 발생")
    @Test
    void errorToken() {
        create();
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + "asda.asdasd.asdasd")
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
