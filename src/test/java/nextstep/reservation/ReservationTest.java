package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.AcceptanceTestExecutionListener;
import nextstep.auth.JwtTokenProvider;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;

import static nextstep.auth.JwtTokenProviderTest.generateToken;
import static nextstep.auth.JwtTokenProviderTest.saveMember;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class ReservationTest {
    public static final String DATE = "2022-08-11";
    public static final String TIME = "13:00";
    public static final String NAME = "name";

    private ReservationRequest request;
    private Long themeId;
    private Long scheduleId;
    private Long memberId;

    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JdbcTemplate jdbcTemplate;
    private String token;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        saveMember(jdbcTemplate);
        ExtractableResponse<Response> tokenResponse = generateToken();
        token = tokenResponse.body().jsonPath().getString("accessToken");

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(scheduleRequest)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);

        request = new ReservationRequest(
                scheduleId,
                jwtTokenProvider.getPrincipal(token)
        );
    }

    @DisplayName("예약을 생성할 수 있다.")
    @Test
    void create() {
        System.out.println(token);
        var response = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }


    @DisplayName("예약을 조회할 수 있다")
    @Test
    void show() {
        createReservation();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .header("Authorization", "Bearer " + token)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제할 수 있다")
    @Test
    void delete() {
        var reservation = createReservation();
        RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .when().delete(reservation.header("Location"))
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복 예약을 생성할 경우, 에러가 발생한다")
    @Test
    void createDuplicateReservation() {
        createReservation();
        ExtractableResponse<Response> response = createReservation();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @DisplayName("예약이 없을 때 예약 목록은 비어있다.")
    @Test
    void showEmptyReservations() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .header("Authorization", "Bearer " + token)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @DisplayName("없는 예약을 삭제할 경우, 에러가 발생한다")
    @Test
    void createNotExistReservation() {
        RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(HttpStatus.LENGTH_REQUIRED.value());
    }


    private ExtractableResponse<Response> createReservation() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }
}
