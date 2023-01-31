package nextstep.member;

import io.restassured.RestAssured;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberE2ETest {
    
    @DisplayName("멤버를 생성한다")
    @Test
    public void create() {
        MemberRequest body = new MemberRequest("username", "password", "name", "010-1234-5678", Role.USER);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("멤버를 조회한다")
    @Test
    public void readMember() {
        createMember("username", "password", "name", "010-1234-5678", Role.USER);
        String token = createToken("username", "password");

        MemberResponse response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(MemberResponse.class);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getUsername()).isEqualTo("username");
        assertThat(response.getName()).isEqualTo("name");
        assertThat(response.getPhone()).isEqualTo("010-1234-5678");
    }

    @DisplayName("유효하지 않는 토큰으로 사용자를 조회할 수 없다")
    @Test
    public void readMemberByInvalidToken() {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token")
                .when().log().all()
                .get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void createMember(String username, String password, String name, String phone, Role role) {
        MemberRequest body = new MemberRequest(username, password, name, phone, role);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    private String createToken(String username, String password) {
        TokenRequest request = new TokenRequest(username, password);

        TokenResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);

        return response.getAccessToken();
    }
}
