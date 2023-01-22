package nextstep.member;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import nextstep.exceptions.exception.notFound.MemberNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {


    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 존재하지_않는_아이디로_검색하면_예외가_발생한다() {
        Long NotExistId = 1L;
        given(memberDao.findById(NotExistId)).willReturn(Optional.empty());
        assertThatThrownBy(() -> memberService.findById(NotExistId))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 존재하지_않는_유저_이름으로_검색하면_예외가_발생한다() {
        String NotExistUserName = "test";
        given(memberDao.findByUsername(NotExistUserName)).willReturn(Optional.empty());
        assertThatThrownBy(() -> memberService.findByUsername(NotExistUserName))
                .isInstanceOf(MemberNotFoundException.class);
    }

}
