package nextstep.member;

import java.util.Optional;
import nextstep.member.dto.MemberRequestDto;
import nextstep.member.dto.MemberResponseDto;
import nextstep.common.exception.DuplicateEntityException;
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
    public static final Member MEMBER = Member.builder()
            .id(1L)
            .username("username")
            .password("password")
            .name("name")
            .phone("010-1234-5678")
            .build();
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberDao memberDao;

    @Test
    @DisplayName("username으로 멤버 찾기 테스트")
    void findByUsernameTest() {

        when(memberDao.findByUsername(anyString())).thenReturn(Optional.of(MEMBER));
        MemberResponseDto memberResponseDto = MemberResponseDto.toDto(MEMBER);
        Assertions.assertThat(memberService.findByUsername(MEMBER.getUsername()))
                .isEqualTo(memberResponseDto);
    }

    @Test
    @DisplayName("member 생성 시 username 중복처리 테스트")
    void createDuplicateMemberTest() {

        MemberRequestDto memberRequestDto = new MemberRequestDto("username", "password", "name", "010-1234-5678", MemberRole.GENERAL.getName());
        when(memberDao.findByUsername(anyString())).thenReturn(Optional.of(MEMBER));
        Assertions.assertThatCode(() -> memberService.create(memberRequestDto))
                .isInstanceOf(DuplicateEntityException.class);
    }
}
