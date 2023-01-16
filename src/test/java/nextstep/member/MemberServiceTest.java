package nextstep.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberDao memberDao;

    @Test
    @DisplayName("username으로 멤버 찾기 테스트")
    void findByUsernameTest() {
        Member findMember = Member.builder()
                .id(1L)
                .username("username")
                .password("password")
                .name("name")
                .phone("010-1234-5678")
                .build();

        when(memberDao.findByUsername(anyString())).thenReturn(findMember);
        MemberResponseDto memberResponseDto = MemberResponseDto.toDto(findMember);
        Assertions.assertThat(memberService.findByUsername(findMember.getUsername()))
                .isEqualTo(memberResponseDto);
    }
}
