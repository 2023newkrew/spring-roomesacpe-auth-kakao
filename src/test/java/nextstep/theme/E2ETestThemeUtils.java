package nextstep.theme;

import io.restassured.RestAssured;
import nextstep.auth.E2ETestAuthUtils;
import nextstep.theme.dto.request.ThemeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class E2ETestThemeUtils {

    public static final String THEME_NAME = "테마이름";
    public static final String THEME_DESC = "테마설명";
    public static final Integer THEME_PRICE = 22000;

    public static Long createTheme() {
        ThemeRequest body = new ThemeRequest(THEME_NAME, THEME_DESC, THEME_PRICE);
        String accessToken = E2ETestAuthUtils.adminLoginAndGetAccessToken();

        String location = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }
}
