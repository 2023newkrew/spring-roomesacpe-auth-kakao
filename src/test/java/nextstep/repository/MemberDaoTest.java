package nextstep.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import nextstep.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

@JdbcTest
class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    private final String NAME = "NAME";
    private final String PHONE = "PHONE";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";

    @BeforeEach
    void setup() {
        if(Objects.isNull(memberDao)){
            memberDao = new MemberDao(jdbcTemplate);
        }
    }

    @Test
    @DisplayName("id로 삭제가 가능하다")
    void delete_by_id_test() {
        // given
        Member member = Member.builder().name(NAME).phone(PHONE).password(PASSWORD).username(USERNAME).build();
        Long id = memberDao.save(member);

        // when
        assertThat(memberDao.findById(id).isPresent()).isTrue();
        Long count = memberDao.deleteById(id);

        // then
        assertThat(count).isEqualTo(1L);
        assertThat(memberDao.findById(id).isEmpty()).isTrue();
    }



}