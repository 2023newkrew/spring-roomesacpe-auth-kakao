package nextstep.member.repository;

import nextstep.member.domain.Member;
import nextstep.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    Optional<MemberEntity> findById(Long id);

    Optional<MemberEntity> findByUsername(String username);
}
