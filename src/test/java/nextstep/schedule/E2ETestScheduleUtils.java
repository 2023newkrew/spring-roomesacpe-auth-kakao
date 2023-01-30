package nextstep.schedule;

import io.restassured.RestAssured;
import nextstep.auth.E2ETestAuthUtils;
import nextstep.schedule.dto.request.ScheduleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class E2ETestScheduleUtils {

    private static final String SCHEDULE_DATE = "2022-08-11";
    private static final String SCHEDULE_TIME = "13:00";

    public static Long createSchedule(Long themeId) {
        ScheduleRequest body = new ScheduleRequest(themeId, SCHEDULE_DATE, SCHEDULE_TIME);
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();
        var location = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
