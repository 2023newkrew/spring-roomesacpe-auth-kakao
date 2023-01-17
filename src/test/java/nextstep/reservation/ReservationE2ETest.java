package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static nextstep.Constant.USERNAME;
import static nextstep.Constant.PASSWORD;
import static nextstep.Constant.NAME;
import static nextstep.Constant.PHONE;
import static nextstep.Constant.AUTHORIZATION;
import static nextstep.Constant.BEARER_TYPE;
import static nextstep.Constant.THEME_NAME;
import static nextstep.Constant.THEME_DESCRIPTION;
import static nextstep.Constant.THEME_PRICE;
import static nextstep.Constant.DATE;
import static nextstep.Constant.TIME;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    private ReservationRequest reservationRequest;
    private Long themeId;
    private String accessToken;

    @BeforeEach
    void setUp() {
        ThemeRequest themeRequest = new ThemeRequest(THEME_NAME, THEME_DESCRIPTION, THEME_PRICE);
        var themeResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(themeRequest)
                .when().post("/themes")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        var scheduleResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(scheduleRequest)
                .when().post("/schedules")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        Long scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);

        MemberRequest memberRequestBody = new MemberRequest(USERNAME, PASSWORD, NAME, PHONE);

        RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(memberRequestBody)
                .when().post("/members")
                .then().statusCode(HttpStatus.CREATED.value())
                .extract();

        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);

        TokenResponse tokenResponse = RestAssured
                .given().contentType(MediaType.APPLICATION_JSON_VALUE).body(tokenRequest)
                .when().post("/login/token")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);

        accessToken = tokenResponse.getAccessToken();

        reservationRequest = new ReservationRequest(
                scheduleId,
                USERNAME
        );
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        var response = RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰 정보가 없으면 예약을 할 수 없다")
    @Test
    void createReservationUnauthorized() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ReservationRequest(1L, "bryan"))
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("예약을 조회한다")
    @Test
    void showReservation() {
        createReservation();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        var reservation = createReservation();
        var response = RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("다른 사람의 예약을 삭제한다")
    @Test
    void delete_other_person() {
        var reservation = createReservation();
        String OTHER_USER_NAME = "Other";

        MemberRequest memberRequestBody = new MemberRequest(OTHER_USER_NAME, PASSWORD, NAME, PHONE);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequestBody)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        TokenRequest body = new TokenRequest(OTHER_USER_NAME, PASSWORD);

        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TokenResponse.class);

        String otherPersonAccessToken = tokenResponse.getAccessToken();

        var response = RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + otherPersonAccessToken)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그인 없이 예약을 삭제한다")
    @Test
    void delete_without_login() {
        var reservation = createReservation();

        var response = RestAssured
                .given().log().all()
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("중복 예약을 생성한다")
    @Test
    void createDuplicateReservation() {
        createReservation();

        var response = RestAssured
                .given()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    @Test
    void showEmptyReservations() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations).isEmpty();
    }

    @DisplayName("없는 예약을 삭제한다")
    @Test
    void createNotExistReservation() {
        var response = RestAssured
                .given().log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> createReservation() {
        return RestAssured
                .given().header(AUTHORIZATION, BEARER_TYPE + accessToken).body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then()
                .extract();
    }
}
