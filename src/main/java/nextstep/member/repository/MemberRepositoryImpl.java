package nextstep.member.repository;

import nextstep.member.dao.MemberDao;
import nextstep.member.datamapper.MemberMapper;
import nextstep.member.domain.Member;
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
    public Optional<MemberEntity> findById(Long id) {

        return Optional.ofNullable(memberDao.findById(id));
    }

    @Override
    public Optional<MemberEntity> findByUsername(String username) {

        return Optional.ofNullable(memberDao.findByUsername(username));
    }
}
