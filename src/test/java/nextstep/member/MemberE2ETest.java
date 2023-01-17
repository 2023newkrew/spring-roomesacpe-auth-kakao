package nextstep.member;

import nextstep.auth.AuthUtil;
import nextstep.auth.TokenRequest;
import nextstep.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MemberE2ETest {

    @DisplayName("이미 존재하지 않는 username으로 멤버를 생성할 수 있다.")
    @Test
    void test1() {
        MemberRequest memberRequest = MemberUtil.NOT_EXIST_MEMBER.toDto();

        MemberUtil.createMemberAndGetValidatableResponse(memberRequest)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("이미 존재하는 username으로는 멤버를 생성할 수 없다.")
    @Test
    void test2() {
        MemberRequest memberRequest = MemberUtil.RESERVATION_EXIST_MEMBER.toDto();

        MemberUtil.createMemberAndGetValidatableResponse(memberRequest)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("토큰을 발급받은 유저는 자신의 정보를 조회할 수 있다.")
    @Test
    void test3() {
        TokenRequest tokenRequest = AuthUtil.RESERVATION_EXIST_USER_TOKEN_REQUEST;
        final TokenResponse tokenResponse = AuthUtil.createToken(tokenRequest);
        final String accessToken = tokenResponse.getAccessToken();

        Member member = MemberUtil.getMemberSelfInfo(accessToken);
        assertThat(member.getUsername()).isEqualTo(tokenRequest.getUsername());
    }

    @DisplayName("토큰이 유효하지 않은 유저는 내 정보를 조회할 수 없다.")
    @Test
    void test4() {
        MemberUtil.getMemberSelfInfoAndGetValidatableResponse("")
                        .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
