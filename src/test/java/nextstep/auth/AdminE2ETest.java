package nextstep.auth;

import nextstep.auth.model.TokenResponse;
import nextstep.member.MemberTestUtil;
import nextstep.schedule.ScheduleTestUtil;
import nextstep.schedule.model.ScheduleRequest;
import nextstep.theme.ThemeTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminE2ETest {
    @DisplayName("Admin 권한을 가진 사용자는 테마를 생성할 수 있다.")
    @Test
    void test1() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_ADMIN_MEMBER);

        ThemeTestUtil.createThemeAndGetValidatableResponse(ThemeTestUtil.DEFAULT_THEME_REQUEST, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("Admin 권한을 가지지 않은 사용자는 테마를 생성할 수 없다.")
    @Test
    void test2() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_MEMBER_MEMBER);

        ThemeTestUtil.createThemeAndGetValidatableResponse(ThemeTestUtil.DEFAULT_THEME_REQUEST, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("Admin 권한을 가진 사용자는 테마를 삭제할 수 있다.")
    @Test
    void test3() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_ADMIN_MEMBER);

        ThemeTestUtil.deleteThemeAndGetValidatableResponse(1L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("Admin 권한을 가지지 않은 사용자는 테마를 삭제할 수 없다.")
    @Test
    void test4() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_MEMBER_MEMBER);

        ThemeTestUtil.deleteThemeAndGetValidatableResponse(1L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("Admin 권한을 가진 사용자는 스케줄을 생성할 수 있다.")
    @Test
    void test5() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_ADMIN_MEMBER);

        ScheduleRequest schedule = new ScheduleRequest(1L, "2022-08-11", "13:00");
        ScheduleTestUtil.createScheduleAndGetValidatableResponse(schedule, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("Admin 권한을 가지지 않은 사용자는 스케줄을 생성할 수 없다.")
    @Test
    void test6() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_MEMBER_MEMBER);

        ScheduleRequest schedule = new ScheduleRequest(1L, "2022-08-11", "13:00");
        ScheduleTestUtil.createScheduleAndGetValidatableResponse(schedule, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("Admin 권한을 가진 사용자는 스케줄을 삭제할 수 있다.")
    @Test
    void test7() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_ADMIN_MEMBER);

        ScheduleTestUtil.deleteScheduleAndGetValidatableResponse(2L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("Admin 권한을 가지지 않은 사용자는 스케줄을 삭제할 수 없다.")
    @Test
    void test8() {
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(MemberTestUtil.ROLE_MEMBER_MEMBER);

        ScheduleTestUtil.deleteScheduleAndGetValidatableResponse(2L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
