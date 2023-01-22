package nextstep.member.service;

import lombok.RequiredArgsConstructor;
import nextstep.member.model.Member;
import nextstep.member.dao.MemberDao;
import nextstep.member.model.MemberRequest;
import nextstep.support.DuplicateEntityException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public Long create(MemberRequest memberRequest) {
        if(memberNameExist(memberRequest)){
            throw new DuplicateEntityException();
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByMemberName(String name) {
        return memberDao.findByMemberName(name);
    }

    private boolean memberNameExist(MemberRequest memberRequest) {
        return findByMemberName(memberRequest.getMemberName()) != null;
    }
}
