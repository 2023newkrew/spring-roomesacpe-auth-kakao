package nextstep.member.repository;

import nextstep.member.domain.Member;
import nextstep.member.domain.MemberWithId;

import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    Optional<Member> findById(Long id);

    Optional<MemberWithId> findByUsername(String username);
}
