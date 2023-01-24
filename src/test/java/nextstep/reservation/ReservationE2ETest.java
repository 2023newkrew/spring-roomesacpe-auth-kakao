package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.LoginUtils;
import nextstep.domain.model.request.TokenRequest;
import nextstep.domain.domain.Reservation;
import nextstep.domain.model.request.ReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static nextstep.auth.LoginUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationE2ETest {
    private final ReservationRequest request = new ReservationRequest(9999L, "브라운");
    private String token;

    @Test
    @DisplayName("유저는 예약을 생성한다.")
    void createByUser() {
        token = loginUser();

        var response = RestAssured
                .given().log().all()
                .body(request)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("예약을 조회한다")
    void show() {
        token = loginUser();
        createReservation();

        var response = RestAssured
                .given().log().all()
                .param("themeId", 9999)
                .param("date", "2022-08-11")
                .auth().oauth2(token)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 삭제한다")
    void delete() {
        token = loginUser();
        var reservation = createReservation();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    @Test
    @DisplayName("중복 예약을 생성한다")
    void createDuplicateReservation() {
        token = loginUser();
        createReservation();

        var response = RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약이 없을 때 예약 목록을 조회한다")
    void showEmptyReservations() {
        token = loginUser();

        var response = RestAssured
                .given().log().all()
                .param("themeId", 9999L)
                .param("date", "2022-08-15")
                .auth().oauth2(token)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("없는 예약을 삭제한다")
    void createNotExistReservation() {
        token = loginUser();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("자신의 예약이 아닌 경우 예약 취소가 불가능하다.")
    void deleteNotOwner() {
        token = loginUser();
        var reservation = createReservation();
        TokenRequest loginBodyAnotherUser = new TokenRequest("anotherUser", "anotherUser");
        String anotherToken = LoginUtils.loginByRequest(loginBodyAnotherUser);

       RestAssured
               .given().log().all()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .auth().oauth2(anotherToken)
               .when().delete(reservation.header("Location"))
               .then().log().all()
               .statusCode(HttpStatus.FORBIDDEN.value());
    }

    private ExtractableResponse<Response> createReservation() {
        return RestAssured
                .given().log().all()
                .body(request)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }
}
