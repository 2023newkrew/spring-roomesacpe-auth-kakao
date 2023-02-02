package nextstep.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("멤버 도메인 테스트")
public class MemberTest {

    @DisplayName("비밀번호가 일치할 경우 false를 반환한다.")
    @Test
    void returnFalseIfRightPassword() {
        String password = "test";
        Member member = new Member("username", password, "name", "010-0000-0000");

        assertThat(member.checkWrongPassword(password)).isFalse();
    }

    @DisplayName("비밀번호가 일치하지 않을 경우 true를 반환한다.")
    @Test
    void returnTrueIfWrongPassword() {
        String password = "test", wrongPassword = "test222";
        Member member = new Member("username", password, "name", "010-0000-0000");

        assertThat(member.checkWrongPassword(wrongPassword)).isTrue();
    }

}
