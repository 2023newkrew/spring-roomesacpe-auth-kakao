package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import nextstep.auth.dto.LoginRequest;
import nextstep.auth.dto.LoginResponse;
import nextstep.auth.dto.TokenRequest;
import nextstep.member.MemberRequest;
import nextstep.member.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AdminE2ETest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String adminToken;


    @BeforeEach
    void setUp() {
        TokenRequest tokenRequest = new TokenRequest(1L, "ADMIN");
        adminToken = jwtTokenProvider.createToken(tokenRequest);
        MemberResponse adminMember = RestAssured
                .given().auth().oauth2(adminToken).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);
        assertThat(adminMember.getRole()).isEqualTo("ADMIN");
    }

    @Test
    void 관리자는_처음_시작할_때_등록된다() {
        LoginRequest loginRequest = new LoginRequest(1L, "password");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 관리자만_유저를_삭제할_수_있다() {
        MemberRequest memberRequest = MemberRequest.builder()
                .username("username")
                .password("password")
                .name("name")
                .phone("010-1234-5678")
                .build();

        String newMemberStringId = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location").split("/")[2];

        LoginRequest loginRequest = new LoginRequest(Long.parseLong(newMemberStringId),
                memberRequest.getPassword());

        String newMemberToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(LoginResponse.class).getAccessToken();


        deleteAndGetResponse(newMemberStringId, null)
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        deleteAndGetResponse(newMemberStringId, newMemberToken)
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteAndGetResponse(newMemberStringId, adminToken)
                .statusCode(HttpStatus.NO_CONTENT.value());


    }

    ValidatableResponse deleteAndGetResponse(String memberId, @Nullable String token) {
        if (token == null) {
            return RestAssured
                    .given().log().all()
                    .when().delete("/admin/members/" + memberId)
                    .then().log().all();
        }
        return RestAssured
                .given().auth().oauth2(token).log().all()
                .when().delete("/admin/members/" + memberId)
                .then().log().all();
    }
}
