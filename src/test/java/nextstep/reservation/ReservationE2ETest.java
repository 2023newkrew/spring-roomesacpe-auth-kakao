package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = {"/test.sql"})
class ReservationE2ETest {
    private String userToken;

    private String username;

    @BeforeEach
    void setUp() {

        userToken = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("userA", "qwer12345"))
                .when().post("/login/token")
                .then()
                .extract().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("예약을 생성한다")
    @Test
    void create() {
        RestAssured.given().log().all()
                .auth().oauth2(userToken)
                .body(new ReservationRequest(2L, "userA"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value()).extract();

    }

    @DisplayName("잘못된 토큰으로 예약 생성")
    @Test
    void createWithInvalidToken() {
        RestAssured.given().log().all()
                .auth().oauth2("abcd")
                .body(new ReservationRequest(2L, "userA"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value()).extract();
    }

    @DisplayName("예약을 조회한다")
    @Test
    void show() {

        var response = RestAssured
                .given().log().all()
                .param("themeId", 1L)
                .param("date", "2023-01-01")
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        var reservation = createReservation(new ReservationRequest(3L, "userA"));

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(userToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("다른 사용자의 예약을 삭제할 수 없음")
    @Test
    void deleteWithInvalidToken() {
        var reservation = createReservation(new ReservationRequest(3L, "userA"));

        String userBToken = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest("userB", "abcd12345"))
                .when().post("/login/token")
                .then()
                .extract().as(TokenResponse.class).getAccessToken();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(userBToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value()).extract();

    }


    @DisplayName("중복 예약을 생성한다")
    @Test
    void createDuplicateReservation() {
        createReservation(new ReservationRequest(3L, "userA"));

        var response = RestAssured
                .given().log().all()
                .body(new ReservationRequest(3L, "userA"))
                .auth().oauth2(userToken)
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
                .param("themeId", 1L)
                .param("date", "2000-01-01")
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @DisplayName("없는 예약을 삭제한다")
    @Test
    void createNotExistReservation() {
        var response = RestAssured
                .given().log().all()
                .auth().oauth2(userToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/100")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> createReservation(ReservationRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .auth().oauth2(userToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }
}
