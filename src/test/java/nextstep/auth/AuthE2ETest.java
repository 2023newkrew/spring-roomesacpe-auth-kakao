package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.auth.utils.JwtTokenProvider;
import nextstep.dto.member.MemberRequest;
import nextstep.dto.member.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String PHONE = "010-1234-5678";
    private Long memberId;

    @Autowired
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @BeforeEach
    void setUp() {
        MemberRequest body = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 생성한다")
    @Test
    public void create() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenResponse.class)).isNotNull();
    }

    @DisplayName("토큰으로 멤버를 조회한다")
    @Test
    void findMemberByToken() {
        // given
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        String token = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class).getAccessToken();

        // when
        MemberResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().get("/members/"+ jwtTokenProvider.getPrincipal(token).getId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberResponse.class);

        // then
        assertThat(response.getId()).isEqualTo(jwtTokenProvider.getPrincipal(token).getId());
        assertThat(response.getUsername()).isEqualTo(USERNAME);
        assertThat(response.getPassword()).isEqualTo(PASSWORD);
        assertThat(response.getName()).isEqualTo(NAME);
        assertThat(response.getPhone()).isEqualTo(PHONE);
    }
}
