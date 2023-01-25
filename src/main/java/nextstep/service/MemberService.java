package nextstep.service;

import java.util.Optional;
import nextstep.exception.NotFoundException;
import nextstep.entity.Member;
import nextstep.dto.member.MemberRequest;
import nextstep.repository.MemberDao;
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
        return memberDao.findById(id).orElseThrow( () -> new NotFoundException("존재하지 않는 아이디 입니다."));
    }

}
