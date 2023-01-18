package nextstep.member;

public interface MemberRepository {
    Long save(Member member);

    Member findById(Long id);

    Member findByUsername(String username);
}
