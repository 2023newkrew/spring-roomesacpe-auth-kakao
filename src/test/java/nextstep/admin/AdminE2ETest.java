package nextstep.admin;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenConfig;
import nextstep.auth.TokenRequest;
import nextstep.member.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminE2ETest {
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Test
    @DisplayName("admin 계정의 JWT이 존재하면 /admin/** 경로에 접근 가능하다.")
    void adminAccessSuccess() {
        // admin 계정에 대해 토큰 발급
        TokenRequest tokenRequest = new TokenRequest(username, password);
        String token = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(String.class);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + token)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value()); // 현재 '/admin'에 해당하는 경로가 설정돼있지 않아서 404 반환
    }

    @Test
    @DisplayName("admin 계정이 아닌 JWT에 대해 /admin/** 경로에 접근 불가하며 403이 반환된다.")
    void adminAccessAbort_Member() {
        // 일반 member 생성
        MemberRequest memberRequest = new MemberRequest("user1", "password", "name", "010-2595-6532");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("members/")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // 일반 member 토큰 발급
        TokenRequest tokenRequest = new TokenRequest(memberRequest.getUsername(), memberRequest.getPassword());
        String memberToken = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(String.class);

        //일반 member 토큰으로 admin url 접속 시도
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, JwtTokenConfig.TOKEN_CLASS + memberToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("admin/")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Authorization token이 없는 상태로 /admin/** 에 접속 불가하며 401 코드가 반환된다.")
    void adminAccessAbort_NoToken() {
        RestAssured.given().log().all()
                .when().get("admin/")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}