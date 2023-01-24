package nextstep.common.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.ThemeRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ThemeProvider {

    public static ExtractableResponse<Response> 테마를_생성한다(String adminAccessToken, ThemeRequest themeRequest) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
        .when()
                .post("/admin/themes")
        .then().log().all()
                .extract();
    }

}
