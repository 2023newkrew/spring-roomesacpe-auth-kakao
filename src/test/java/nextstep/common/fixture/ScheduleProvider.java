package nextstep.common.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.ScheduleRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ScheduleProvider {

    public static ExtractableResponse<Response> 스케줄을_생성한다(String adminAccessToken, ScheduleRequest scheduleRequest) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminAccessToken)
                .body(scheduleRequest)
        .when()
                .post("/admin/schedules")
        .then()
                .extract();
    }

}
