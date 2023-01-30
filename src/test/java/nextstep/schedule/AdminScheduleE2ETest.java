package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.auth.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:/test.sql")
public class AdminScheduleE2ETest {

    private String token;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.createToken("1");
    }

    @DisplayName("스케줄을 생성한다")
    @Test
    public void createSchedule() {
        ScheduleRequest body = new ScheduleRequest(1L, "2022-08-11", "14:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", 1L)
                .param("date", "2022-08-11")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().get("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(2);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void delete() {
        var response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().delete("/admin/schedules/1")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
