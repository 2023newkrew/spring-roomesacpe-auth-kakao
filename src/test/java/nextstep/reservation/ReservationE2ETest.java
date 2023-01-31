package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationE2ETest {
    private String RESERVATION_EXIST_USER_ACCESS_TOKEN;

    @BeforeEach
    void setUp() {
        RESERVATION_EXIST_USER_ACCESS_TOKEN = AuthUtil.createTokenForReservationExistUser();
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        ReservationRequest reservationRequest = new ReservationRequest(3L);

        var response = createReservation(reservationRequest, RESERVATION_EXIST_USER_ACCESS_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("인증되지 않은 사용자는 예약을 할 수 없다")
    @Test
    void createUnauthenticated() {
        ReservationRequest reservationRequest = new ReservationRequest(3L);

        var response = createReservation(reservationRequest, "");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("예약을 조회한다")
    @Test
    void show() {
        var response = getReservations(1L, "2022-11-11");

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(2);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        ExtractableResponse<Response> response = removeReservation(1L, RESERVATION_EXIST_USER_ACCESS_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("인증되지 않은 사용자는 예약을 삭제할 수 없다")
    @Test
    void deleteUnauthenticated() {
        ExtractableResponse<Response> response = removeReservation(1L, "");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("중복 예약을 생성한다")
    @Test
    void createDuplicateReservation() {
        ReservationRequest reservationRequest = new ReservationRequest(1L);

        var response = createReservation(reservationRequest, RESERVATION_EXIST_USER_ACCESS_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    @Test
    void showEmptyReservations() {
        var response = getReservations(1L, "2023-01-01");

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @DisplayName("없는 예약을 삭제한다")
    @Test
    void createNotExistReservation() {
        var response = removeReservation(10L, RESERVATION_EXIST_USER_ACCESS_TOKEN);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> createReservation(ReservationRequest reservationRequest, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> removeReservation(Long id, String accessToken) {
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .extract();
        return response;
    }

    private ExtractableResponse<Response> getReservations(Long themeId, String date) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(RESERVATION_EXIST_USER_ACCESS_TOKEN)
                .param("themeId", themeId)
                .param("date", date)
                .when().get("/reservations")
                .then().log().all()
                .extract();
    }
}
