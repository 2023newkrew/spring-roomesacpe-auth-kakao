package nextstep.member.service;

import java.util.Optional;
import nextstep.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.dto.MemberRequest;
import nextstep.member.repository.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Optional<Member> getByNameAndPassword(String username, String password) {
        return this.memberDao.findByUsernameAndPassword(username, password);
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id).orElseThrow( () -> new NotExistEntityException("존재하지 않는 아이디 입니다."));
    }
}
