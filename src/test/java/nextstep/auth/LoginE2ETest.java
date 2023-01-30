package nextstep.auth;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("classpath:/test.sql")
public class LoginE2ETest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private String token;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.createToken("2");
    }

    @DisplayName("토큰을 생성한다")
    @Test
    public void create() {
        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenResponse.class)).isNotNull();
    }
}
