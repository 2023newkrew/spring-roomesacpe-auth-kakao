package nextstep.member.dao;

import nextstep.member.entity.MemberEntity;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.Optional;

public interface MemberDao {
    Long save(MemberEntity member);

    Optional<MemberEntity> findById(Long id);

    Optional<MemberEntity> findByUsername(String username);
}
