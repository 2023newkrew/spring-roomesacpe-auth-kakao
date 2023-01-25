package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import nextstep.dto.member.MemberRequest;
import nextstep.exception.NotFoundException;
import nextstep.repository.MemberDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberServiceTest {


    private final String NAME = "NAME";
    private final String PHONE = "PHONE";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    MemberService memberService;

    @BeforeEach
    void setup() {
        if (Objects.isNull(memberService)) {
            memberService = new MemberService(new MemberDao(jdbcTemplate));
        }
    }


    @Test
    @DisplayName("id로 삭제가 가능하다")
    void delete_by_id_test() {
        // given
        Long id = memberService.create(new MemberRequest(USERNAME, PASSWORD, NAME, PHONE));
        // when
        assertThat(memberService.findById(id)).isNotNull();
        Long count = memberService.deleteById(id);

        // then
        assertThat(count).isEqualTo(1L);
        Assertions.assertThatThrownBy(() -> memberService.findById(id)).isInstanceOf(NotFoundException.class);
    }


}