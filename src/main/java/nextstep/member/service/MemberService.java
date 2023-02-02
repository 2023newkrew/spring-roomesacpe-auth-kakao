package nextstep.member.service;

import nextstep.exception.NoSuchRoleException;
import nextstep.member.domain.Role;
import nextstep.member.repository.MemberDao;
import nextstep.member.dto.MemberRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        checkValidRole(memberRequest.getRole());
        return memberDao.save(memberRequest.toEntity()); }

    private void checkValidRole(String role) {
        for (Role c : Role.values()) {
            if (c.name().equals(role)) {
                return;
            }
        }
        throw new NoSuchRoleException();
    }
}
