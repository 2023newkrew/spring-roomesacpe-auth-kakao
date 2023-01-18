package nextstep.member.repository;

import nextstep.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {

    Long save(MemberEntity memberEntity);

    Optional<MemberEntity> findById(Long id);

    Optional<MemberEntity> findByUsername(String username);
}
