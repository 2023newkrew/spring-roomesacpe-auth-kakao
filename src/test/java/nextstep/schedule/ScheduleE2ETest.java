package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.theme.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static nextstep.admin.AdminE2ETest.*;
import static nextstep.reservation.ReservationE2ETest.DATE;
import static nextstep.reservation.ReservationE2ETest.TIME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScheduleE2ETest {

    private Long themeId;

    @BeforeEach
    void setUp() {
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        themeId = createThemeWithAdminAuthority(themeRequest);
    }

    @DisplayName("스케줄을 조회한다")
    @Test
    public void showSchedules() {
        // given
        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, DATE, TIME);
        createScheduleWithAdminAuthority(scheduleRequest);

        var response = RestAssured
                .given().log().all()
                .param("themeId", themeId)
                .param("date", "2022-08-11")
                .when().get("/schedules")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList(".").size()).isEqualTo(1);
    }
}
