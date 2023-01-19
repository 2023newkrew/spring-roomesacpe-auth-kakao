package nextstep.member.service;

import nextstep.auth.dto.TokenRequest;
import nextstep.member.entity.Member;
import nextstep.member.dao.MemberDao;
import nextstep.member.dto.MemberRequest;
import nextstep.global.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

    public void validateUserPassword(TokenRequest tokenRequest) {
        if (!memberDao.isUsernameAndPasswordMatch(
                tokenRequest.getUsername(),
                tokenRequest.getPassword())) {
            throw new NotExistEntityException();
        }
    }
}
