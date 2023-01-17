package nextstep.member.repository;

import nextstep.member.domain.Member;
import nextstep.member.domain.MemberForAuth;

import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByUsernameAndPassword(MemberForAuth memberForAuth);

    Optional<Member> findByUsernameAndPassword(String username, String password);
}
