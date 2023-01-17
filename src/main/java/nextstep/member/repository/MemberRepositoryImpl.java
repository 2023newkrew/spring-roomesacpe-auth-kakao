package nextstep.member.repository;

import nextstep.member.dao.MemberDao;
import nextstep.member.datamapper.MemberMapper;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberForAuth;
import nextstep.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

        return entityToOptionalDomain(memberEntity);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        MemberEntity memberEntity = memberDao.findByUsername(username);

        return entityToOptionalDomain(memberEntity);
    }

    @Override
    public Optional<Member> findByUsernameAndPassword(MemberForAuth memberForAuth) {
        MemberEntity memberEntity = memberDao.findByUsernameAndPassword(memberForAuth.getUsername(), memberForAuth.getPassword());

        return entityToOptionalDomain(memberEntity);
    }

    @Override
    public Optional<Member> findByUsernameAndPassword(String username, String password) {
        MemberEntity memberEntity = memberDao.findByUsernameAndPassword(username, password);

        return entityToOptionalDomain(memberEntity);
    }

    private Optional<Member> entityToOptionalDomain(MemberEntity memberEntity) {
        return Optional.ofNullable(memberEntity)
                .map(MemberMapper.INSTANCE::entityToDomain);
    }
}
