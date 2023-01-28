package nextstep.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@DisplayName("멤버 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("username으로 사용자를 조회하는 기능")
    @Test
    void findByUsername() {
        String username = "test";
        String password = "password";
        String name = "testName";
        String phone = "010-0000-0000";
        Member member = new Member(username, password, name, phone);

        given(memberDao.findByUsername(username)).willReturn(member);

        assertThat(memberService.findByUsername(username)).usingRecursiveComparison().isEqualTo(member);
    }
}
