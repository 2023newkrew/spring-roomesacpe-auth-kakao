package nextstep.member;

import nextstep.exception.BusinessException;
import nextstep.exception.CommonErrorCode;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        if (memberDao.findByUsername(memberRequest.getUsername()) != null) {
            throw new BusinessException(CommonErrorCode.DUPLICATE_ENTITY);
        }
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }
}
