package nextstep.reservations;

import io.restassured.RestAssured;
import nextstep.reservations.dto.auth.TokenRequestDto;
import nextstep.reservations.dto.member.MemberRequestDto;
import nextstep.reservations.repository.reservation.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema/schema.sql", "classpath:data/init.sql"})
public class ReservationTest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @LocalServerPort
    int port;

    @Autowired
    ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약_생성() {
        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("date", "2022-08-01");
        reservationRequest.put("time", "13:00");
        reservationRequest.put("name", "name");
        reservationRequest.put("themeName", "워너고홈");
        reservationRequest.put("themeDesc", "병맛 어드벤처 회사 코믹물");
        reservationRequest.put("themePrice", 29_000);

        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + accessToken)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/3");
    }

    @Test
    void 예약_조회() {
        RestAssured.given().log().all()
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", notNullValue())
                .body("date", notNullValue())
                .body("time", notNullValue())
                .body("name", notNullValue())
                .body("themeName", notNullValue())
                .body("themeDesc", notNullValue())
                .body("themePrice", notNullValue());
    }

    @Test
    void 예약_삭제() {
        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 타인의_예약_삭제() {
        MemberRequestDto body = new MemberRequestDto("사용자1", "1234", "name", "010-1234-5678");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        String accessToken = getAccessToken("사용자1", "1234");

        RestAssured.given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("권한이 없습니다."));
    }

    @Test
    void 중복_예약_오류() {
        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("date", "1982-02-19");
        reservationRequest.put("time", "13:00");
        reservationRequest.put("name", "name");
        reservationRequest.put("themeName", "워너고홈");
        reservationRequest.put("themeDesc", "병맛 어드벤처 회사 코믹물");
        reservationRequest.put("themePrice", 29_000);

        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + accessToken)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("해당 시간에 해당 테마의 중복된 예약이 있습니다."));
    }

    @Test
    void 예약할_수_없는_시간_오류() {
        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("date", "2022-08-01");
        reservationRequest.put("time", "14:00");
        reservationRequest.put("name", "name");
        reservationRequest.put("themeName", "워너고홈");
        reservationRequest.put("themeDesc", "병맛 어드벤처 회사 코믹물");
        reservationRequest.put("themePrice", 29_000);

        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + accessToken)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("예약할 수 없는 시간입니다."));
    }

    @Test
    void 존재하지_않는_예약_조회_오류() {
        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().get("/reservations/100")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 예약입니다."));
    }

    @Test
    void 존재하지_않는_예약_삭제_오류() {
        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/100")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 예약입니다."));
    }

    @Test
    void 존재하지_않는_테마_예약_오류() {
        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("date", "2022-08-01");
        reservationRequest.put("time", "13:00");
        reservationRequest.put("name", "name");
        reservationRequest.put("themeName", "카카오");
        reservationRequest.put("themeDesc", "카카오 어드벤처");
        reservationRequest.put("themePrice", 29_000);

        String accessToken = getAccessToken(USERNAME, PASSWORD);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + accessToken)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 테마입니다."));
    }

    @Test
    void 잘못된_토큰으로_예약_시도() {
        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("date", "2022-08-01");
        reservationRequest.put("time", "13:00");
        reservationRequest.put("name", "name");
        reservationRequest.put("themeName", "워너고홈");
        reservationRequest.put("themeDesc", "병맛 어드벤처 회사 코믹물");
        reservationRequest.put("themePrice", 29_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("authorization", "Bearer " + "wrongtoken")
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 사용자입니다."));
    }

    @Test
    void 토큰_없이_예약_시도() {
        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("date", "2022-08-01");
        reservationRequest.put("time", "13:00");
        reservationRequest.put("name", "name");
        reservationRequest.put("themeName", "워너고홈");
        reservationRequest.put("themeDesc", "병맛 어드벤처 회사 코믹물");
        reservationRequest.put("themePrice", 29_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("권한이 없습니다."));
    }

    private static String getAccessToken(String username, String password) {
        TokenRequestDto body = new TokenRequestDto(username, password);
        String accessToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().get("accessToken");
        return accessToken;
    }
}
