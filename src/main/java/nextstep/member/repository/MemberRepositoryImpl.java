package nextstep.member.repository;

import nextstep.member.dao.MemberDao;
import nextstep.member.domain.Member;
import nextstep.member.entity.MemberEntity;
import nextstep.member.mapper.MemberMapper;
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
    public Long save(MemberEntity memberEntity) {

        return memberDao.save(memberEntity);
    }

    @Override
    public Optional<Member> findById(Long id) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToDomain(memberDao.findById(id)));
    }

    @Override
    public Optional<Member> findByUsername(String username) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToDomain(memberDao.findByUsername(username)));
    }
}
