package nextstep.reservations;

import io.restassured.RestAssured;
import nextstep.reservations.dto.auth.TokenReponseDto;
import nextstep.reservations.dto.auth.TokenRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Test
    public void 토큰을_생성한다() {
        TokenRequestDto body = new TokenRequestDto(USERNAME, PASSWORD);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.as(TokenReponseDto.class)).isNotNull();
    }

    @Test
    public void 잘못된_비밀번호로_토큰을_생성한다() {
        TokenRequestDto body = new TokenRequestDto(USERNAME, "WRONG PASSWORD");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("권한이 없습니다."));
    }
}
