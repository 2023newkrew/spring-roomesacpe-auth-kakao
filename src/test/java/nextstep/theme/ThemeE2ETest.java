package nextstep.theme;

import nextstep.auth.AuthTestUtil;
import nextstep.auth.TokenResponse;
import nextstep.member.Member;
import nextstep.member.MemberTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ThemeE2ETest {

    @DisplayName("인증된 사용자는 테마를 생성할 수 있다.")
    @Test
    void test1() {
        Member ReservationExistUser = MemberTestUtil.getReservationExistMember(1L);
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(ReservationExistUser);

        ThemeTestUtil.createThemeAndGetValidatableResponse(ThemeTestUtil.DEFAULT_THEME_REQUEST, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("인증되지 않은 사용자는 테마를 생성할 수 없다.")
    @Test
    void test2() {
        ThemeTestUtil.createThemeAndGetValidatableResponse(ThemeTestUtil.DEFAULT_THEME_REQUEST, "")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("인증 유무와 관계 없이 테마 목록을 조회할 수 았다.")
    @Test
    void test3() {
        List<Theme> themes = ThemeTestUtil.getThemes("2022-11-11");
        assertThat(themes).hasSize(2);
    }

    @DisplayName("인증된 사용자는 테마를 삭제할 수 있다.")
    @Test
    void test4() {
        Member ReservationExistUser = MemberTestUtil.getReservationExistMember(1L);
        TokenResponse tokenResponse = AuthTestUtil.tokenLogin(ReservationExistUser);

        ThemeTestUtil.deleteThemeAndGetValidatableResponse(1L, tokenResponse.getAccessToken())
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("인증되지 않은 사용자는 테마를 삭제할 수 없다.")
    @Test
    void test5() {
        ThemeTestUtil.deleteThemeAndGetValidatableResponse(1L, "")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
