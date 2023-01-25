package nextstep.reservations;

import io.restassured.RestAssured;
import nextstep.reservations.dto.member.MemberRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @BeforeEach
    void setUp() {
        MemberRequestDto body = new MemberRequestDto(USERNAME, PASSWORD, "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

//    @DisplayName("토큰을 생성한다")
//    @Test
//    public void create() {
//        TokenRequest body = new TokenRequest(USERNAME, PASSWORD);
//        var response = RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(body)
//                .when().post("/login/token")
//                .then().log().all()
//                .statusCode(HttpStatus.OK.value())
//                .extract();
//
//        assertThat(response.as(TokenResponse.class)).isNotNull();
//    }
}
