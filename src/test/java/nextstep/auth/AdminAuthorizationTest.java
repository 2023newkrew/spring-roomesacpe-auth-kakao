package nextstep.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.AcceptanceTestExecutionListener;
import nextstep.member.MemberRequest;
import nextstep.member.Role;
import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationRequest;
import nextstep.schedule.ScheduleRequest;
import nextstep.theme.ThemeRequest;
import org.assertj.core.api.Assertions;
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

import static nextstep.auth.Interceptor.LoginInterceptor.authorization;
import static nextstep.auth.Interceptor.LoginInterceptor.bearer;
import static nextstep.auth.JwtTokenProviderTest.*;
import static nextstep.reservation.ReservationTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Admin 권한 테스트")
@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class AdminAuthorizationTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private ReservationRequest request;
    private Long themeId;
    private Long scheduleId;
    private String token;

    @BeforeEach
    void setUp() {
        saveMember(jdbcTemplate, USERNAME, PASSWORD, Role.MEMBER);
        ExtractableResponse<Response> tokenResponse = generateToken(USERNAME, PASSWORD);
        token = tokenResponse.body().jsonPath().getString("accessToken");

        saveMember(jdbcTemplate, USERNAME + "admin", PASSWORD, Role.ADMIN);
        ExtractableResponse<Response> tokenAdminResponse = generateToken(USERNAME + "admin", PASSWORD);
        String adminToken = tokenAdminResponse.body().jsonPath().getString("accessToken");
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 10000);
        var themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + adminToken)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, TEST_DATE, TEST_TIME);
        var scheduleResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + adminToken)
                .body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);
    }

    @DisplayName("일반 멤버는 예약을 등록할 수 있다")
    @Test
    void registerReservationMemberTest() {
        ExtractableResponse<Response> reservation = requestCreateReservation();
        Assertions.assertThat(reservation.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("일반 멤버는 자신의 예약을 조회할 수 있다")
    @Test
    void lookUpReservationMemberTest() {
        requestCreateReservation();
        var reservation = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", TEST_DATE)
                .header(authorization, bearer + token)
                .when().get("/reservations")
                .then().log().all()
                .extract();
        List<Reservation> reservations = reservation.jsonPath().getList(".", Reservation.class);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("일반 멤버는 자신의 예약을 삭제할 수 있다")
    @Test
    void deleteReservationMemberTest() {
        ExtractableResponse<Response> reservation = requestCreateReservation();
        RestAssured
                .given().log().all()
                .header(authorization, bearer + token)
                .when().delete(reservation.header("Location"))
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("일반 멤버는 테마를 등록할 수 없다")
    @Test
    void nonRegisterThemeMemberTest() {
        ExtractableResponse<Response> createTheme = requestCreateTheme();
        Assertions.assertThat(createTheme.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("일반 멤버는 테마를 조회할 수 있다")
    @Test
    void lookUpThemeMemberTest() {
        ExtractableResponse<Response> lookUpTheme = RestAssured
                .given().log().all()
                .param("date", "2022-08-11")
                .header(authorization, bearer + token)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(lookUpTheme.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("일반 멤버는 테마를 삭제할 수 없다")
    @Test
    void nonDeleteThemeMemberTest() {
        RestAssured
                .given().log().all()
                .header(authorization, bearer + token)
                .when().delete("/admin/themes/" + themeId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("일반 멤버는 스케줄을 등록할 수 없다")
    @Test
    void nonRegisterScheduleMemberTest() {
        ExtractableResponse<Response> createSchedule = requestCreateSchedule();
        Assertions.assertThat(createSchedule.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("일반 멤버는 스케줄을 조회할 수 있다")
    @Test
    void lookUpScheduleMemberTest() {
        ExtractableResponse<Response> lookUpSchedule = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .header(authorization, bearer + token)
                .param("date", "2022-08-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(lookUpSchedule.jsonPath().getList(".").size()).isEqualTo(1);
    }

    @DisplayName("일반 멤버는 스케줄을 삭제할 수 없다")
    @Test
    void NonDeleteScheduleMemberTest() {
        RestAssured
                .given().log().all()
                .header(authorization, bearer + token)
                .when().delete("/admin/schedules/" + scheduleId)
                .then().log().all()
                .extract();
    }

    @DisplayName("Admin 유저는 일반 유저를 Admin으로 변경할 수 있다")
    @Test
    void createAdmin(){
        saveMember(jdbcTemplate, "userAdmin", "1234", Role.ADMIN);
        ExtractableResponse<Response> response = generateToken("userAdmin", "1234");
        String accessToken = response.body().jsonPath().getString("accessToken");

        saveMember(jdbcTemplate, "userMember", "1234", Role.MEMBER);
        MemberRequest body = new MemberRequest("userMember", "1234", "name", "010-1234-5678");
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + accessToken)
                .body(body)
                .when().post("/admin/register/")
                .then().log().all()
                .extract();
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("일반 유저는 다른 일반 유저를 Admin으로 변경할 수 없다")
    @Test
    void nonCreateAdmin(){
        saveMember(jdbcTemplate, "userAdmin", "1234", Role.MEMBER);
        ExtractableResponse<Response> response = generateToken("userAdmin", "1234");
        String accessToken = response.body().jsonPath().getString("accessToken");

        saveMember(jdbcTemplate, "userMember", "1234", Role.MEMBER);
        saveMember(jdbcTemplate, "userMember", "1234", Role.MEMBER);
        MemberRequest body = new MemberRequest("userMember", "1234", "name", "010-1234-5678");
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + accessToken)
                .body(body)
                .when().post("/admin/register/")
                .then().log().all()
                .extract();
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> requestCreateReservation() {
        request = new ReservationRequest(
                scheduleId,
                jwtTokenProvider.getPrincipal(bearer + token)
        );
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestCreateTheme() {
        ThemeRequest body = new ThemeRequest("테마이름111", "테마설명", 22000);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + token)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> requestCreateSchedule() {
        ScheduleRequest body = new ScheduleRequest(themeId, "2022-08-11", "13:00:00");
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(authorization, bearer + token)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .extract();
    }
}

