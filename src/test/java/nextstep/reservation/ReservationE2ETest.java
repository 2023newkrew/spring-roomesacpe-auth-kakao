package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import nextstep.member.MemberRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.support.DatabaseCleaner;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    @Autowired
    private DatabaseCleaner databaseCleaner;

    private final ReservationRequest request = new ReservationRequest(1L, "name");
    private String accessToken;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        databaseCleaner.insertInitialData();

        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("username", "password"))
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        accessToken = tokenResponse.body().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰없이 예약을 생성하면 401 반환")
    @Test
    void createReservationWithoutToken() {
        var response = RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("예약을 조회한다")
    @Test
    void show() {
        createReservation(request);

        var response = RestAssured
                .given().log().all()
                .param("themeId", 1L)
                .param("date", "2022-08-11")
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("다른 사람의 예약을 삭제한다")
    @Test
    void deleteOtherPeopleReservationExceptionTest() {
        String newPersonToken = createNewPersonToken();

        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + newPersonToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("중복 예약을 생성한다")
    @Test
    void createDuplicateReservation() {
        createReservation(request);

        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("없는 예약을 삭제한다")
    @Test
    void createNotExistReservation() {
        RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        var response = RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("토큰없이 예약을 삭제한다")
    @Test
    void deleteReservationWithoutToken() {
        createReservation(request);

        var response = RestAssured
                .given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> createReservation(ReservationRequest request) {
        return RestAssured
                .given().log().all()
                .header("authorization", "Bearer " + accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }

    private String createNewPersonToken() {

        MemberRequest body = new MemberRequest("username2", "password", "name", "010-1234-5678");
        var memberResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        var tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("username2", "password"))
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        return tokenResponse.body().as(TokenResponse.class).getAccessToken();
    }
}
