package nextstep.member;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberDao memberDao;

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
        return memberDao.findById(id).orElseThrow( () -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
    }
}
