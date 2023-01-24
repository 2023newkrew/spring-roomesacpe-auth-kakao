package nextstep.member.repository;

import nextstep.member.domain.Member;
import nextstep.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {

    Long save(MemberEntity memberEntity);

    Optional<Member> findById(Long id);

    Optional<Member> findByUsername(String username);
}
