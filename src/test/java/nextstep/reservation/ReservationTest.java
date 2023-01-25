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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static nextstep.auth.JwtTokenProviderTest.*;
import static nextstep.auth.authorization.LoginInterceptor.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class ReservationTest {
    public static final String DATE = "2022-08-11";
    public static final String TIME = "13:00";
    public static final String NAME = "name";

    private ReservationRequest request;
    private Long themeId;
    private Long scheduleId;

    @InjectMocks
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    Environment env;

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String token;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", env.getProperty("jwt.secret"));
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", env.getProperty("jwt.validateMilliSeconds"));

        saveMember(jdbcTemplate, USERNAME, PASSWORD);
        ExtractableResponse<Response> tokenResponse = generateToken(USERNAME, PASSWORD);
        token = tokenResponse.body().jsonPath().getString("accessToken");

        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + token)
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
                .header(authorization, bearer + token)
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

    @DisplayName("허용되지 않은 사용자가 예약을 이용할 때, 에러가 발생한다")
    @Test
    public void notAuthorizedUserTest(){
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("스케줄이 있는 경우, 예약을 생성할 수 있다.")
    @Test
    void create() {
        RestAssured
            .given().log().all()
            .header(authorization, bearer + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스케줄이 없는 경우, 예약을 생성하면 에러가 발생한다.")
    @Test
    void createError() {
        request = new ReservationRequest(
                scheduleId+2,
                jwtTokenProvider.getPrincipal(token)
        );
        RestAssured
            .given().log().all()
            .header(authorization, bearer + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(HttpStatus.LENGTH_REQUIRED.value());
    }

    @DisplayName("중복 예약을 생성할 경우, 에러가 발생한다")
    @Test
    void createDuplicateReservation() {
        requestCreateReservation();
        ExtractableResponse<Response> createReservation = requestCreateReservation();
        assertThat(createReservation.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
    }


    @DisplayName("예약을 조회할 수 있다")
    @Test
    void show() {
        requestCreateReservation();

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .header(authorization, bearer + token)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("예약이 없을 때 예약 목록은 비어있다.")
    @Test
    void showEmptyReservations() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", DATE)
                .header(authorization, bearer + token)
                .when().get("/reservations")
                .then().log().all()
                .extract();

        List<Reservation> reservations = response.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @DisplayName("예약을 삭제할 수 있다")
    @Test
    void delete() {
        var reservation = requestCreateReservation();
        RestAssured
            .given().log().all()
            .header(authorization, bearer + token)
            .when().delete(reservation.header("Location"))
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("없는 예약을 삭제할 경우, 에러가 발생한다")
    @Test
    void createNotExistReservation() {
        RestAssured
            .given().log().all()
            .header(authorization, bearer + token)
            .when().delete("/reservations/22221")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("다른 회원이 삭제하는 경우, 에러가 발생한다")
    @Test
    void otherUserDeleteTest(){
        var reservation = requestCreateReservation();
        String otherUserName = USERNAME + "22";
        saveMember(jdbcTemplate, otherUserName, PASSWORD);
        ExtractableResponse<Response> otherTokenResponse = generateToken(otherUserName, PASSWORD);
        String otherToken = otherTokenResponse.body().jsonPath().getString("accessToken");

        RestAssured
                .given().log().all()
                .header(authorization, bearer + otherToken)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> requestCreateReservation() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }
}
