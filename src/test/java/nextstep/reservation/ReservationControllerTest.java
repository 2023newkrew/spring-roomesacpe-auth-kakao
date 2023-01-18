package nextstep.reservation;


import io.jsonwebtoken.JwtException;
import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.exception.NotExistEntityException;
import nextstep.support.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    private final String validAccessToken = "valid_token";
    private final String invalidAccessToken = "invalid_token";
    @LocalServerPort
    int port;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private ReservationService reservationService;
    private ReservationRequest request;
    private final Long scheduleId = 1L;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        request = new ReservationRequest(
                scheduleId);
    }

    @Test
    @DisplayName("예약을 생성한다")
    void createTest() {
        when(jwtTokenProvider.getPrincipal(validAccessToken))
                .thenReturn("username");

        when(reservationService.create(request, "username"))
                .thenReturn(1L);

        RestAssured
                .given()
                .log()
                .all()
                .auth()
                .oauth2(validAccessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/reservations")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value());

    }


    @Test
    @DisplayName("예약을 생성할 때 액세스 토큰이 유효해야한다.")
    void createWithInvalidAccessToken() {
        when(jwtTokenProvider.getPrincipal(invalidAccessToken)).thenThrow(JwtException.class);

        RestAssured
                .given()
                .log()
                .all()
                .auth()
                .oauth2(invalidAccessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/reservations")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("예약을 생성할 때 액세스 토큰이 존재해야 한다.")
    void createWithoutAccessToken() {
        RestAssured
                .given()
                .log()
                .all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/reservations")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("권한을 가진 예약만 삭제할 수 있다.")
    void deleteWithInvalidAccessTokenTest() {
        when(jwtTokenProvider.getPrincipal(validAccessToken))
                .thenReturn("username");
        doThrow(UnauthorizedException.class).when(reservationService)
                .deleteById(1L, "username");

        RestAssured
                .given()
                .log()
                .all()
                .auth()
                .oauth2(validAccessToken)
                .when()
                .delete("/reservations/1")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("존재하는 예약만 삭제할 수 있다.")
    void deleteNotExistReservationTest() {
        when(jwtTokenProvider.getPrincipal(validAccessToken))
                .thenReturn("username");
        doThrow(NotExistEntityException.class).when(reservationService)
                .deleteById(1L, "username");

        RestAssured
                .given()
                .log()
                .all()
                .auth()
                .oauth2(validAccessToken)
                .when()
                .delete("/reservations/1")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
