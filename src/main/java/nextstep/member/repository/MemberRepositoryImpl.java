package nextstep.member.repository;

import nextstep.member.dao.MemberDao;
import nextstep.member.datamapper.MemberMapper;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberWithId;
import nextstep.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberDao memberDao;

    @Autowired
    public MemberRepositoryImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Long save(Member member) {

        return memberDao.save(MemberMapper.INSTANCE.domainToEntity(member));
    }

    @Override
    public Optional<Member> findById(Long id) {
        MemberEntity memberEntity = memberDao.findById(id);

        return entityToOptionalDomain(memberEntity, MemberMapper.INSTANCE::entityToDomain);
    }

    @Override
    public Optional<MemberWithId> findByUsername(String username) {
        MemberEntity memberEntity = memberDao.findByUsername(username);

        return entityToOptionalDomain(memberEntity, MemberMapper.INSTANCE::entityToDomainWithId);
    }

    private <T> Optional<T> entityToOptionalDomain(MemberEntity memberEntity, Function<MemberEntity, T> function) {

        return Optional.ofNullable(memberEntity)
                .map(function);
    }
}
