package nextstep.schedule;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleE2ETest {

    @DisplayName("스케줄을 생성한다")
    @Test
    void createSchedule() {
        ScheduleRequest body = new ScheduleRequest(1L, "2022-08-11", "13:00");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    void showSchedules() {
        var response = RestAssured
                .given().log().all()
                .param("themeId", 1L)
                .param("date", "2022-11-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(3);
    }

    @DisplayName("스케줄을 삭제한다")
    @Test
    void delete() {
        var response = RestAssured
                .given().log().all()
                .when().delete("/schedules/2")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
