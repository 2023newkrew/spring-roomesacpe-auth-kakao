package nextstep.member;

import nextstep.auth.TokenRequestDto;
import nextstep.support.exception.DuplicateEntityException;
import nextstep.support.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
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
        when(memberDao.findByUsername(anyString())).thenReturn(MEMBER);
        MemberResponseDto memberResponseDto = MemberResponseDto.toDto(MEMBER);
        assertThat(memberService.findByUsername(MEMBER.getUsername()))
                .isEqualTo(memberResponseDto);
    }

    @Test
    @DisplayName("tokenRequest 유효성 확인 성공 테스트")
    void validateTokenTest() {
        when(memberDao.findByUsernameAndPassword(anyString(), anyString())).thenReturn(MEMBER);
        TokenRequestDto tokenRequestDto = new TokenRequestDto(MEMBER.getUsername(), MEMBER.getPassword());
        memberService.validateIdAndPassword(tokenRequestDto);
        assertThatNoException();
    }

    @Test
    @DisplayName("tokenRequest 유효성 확인 실패시 예외 발생 테스트")
    void validateTokenThrowNotExistEntityExceptionTest() {
        when(memberDao.findByUsernameAndPassword(anyString(), anyString())).thenReturn(null);
        TokenRequestDto tokenRequestDto = new TokenRequestDto(MEMBER.getUsername(), MEMBER.getPassword());
        assertThatThrownBy(() -> memberService.validateIdAndPassword(tokenRequestDto)).isInstanceOf(UnauthorizedException.class);
    }

    @Test
    @DisplayName("member 생성 시 username 중복처리 테스트")
    void createDuplicateMemberTest() {

        MemberRequest memberRequest = new MemberRequest("username", "password", "name", "010-1234-5678");
        when(memberDao.findByUsername(anyString())).thenReturn(MEMBER);
        assertThatCode(() -> memberService.create(memberRequest))
                .isInstanceOf(DuplicateEntityException.class);
    }
}
