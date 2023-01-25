package nextstep.member;

import nextstep.exception.BusinessErrorCode;
import nextstep.exception.BusinessException;
import nextstep.exception.DataAccessErrorCode;
import nextstep.exception.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long create(MemberRequest memberRequest) {
        if (isAlreadyExistMember(memberRequest.getUsername())) {
            throw new BusinessException(BusinessErrorCode.MEMBER_ALREADY_EXIST_BY_USERNAME);
        }
        return memberDao.save(memberRequest.toEntity());
    }

    private boolean isAlreadyExistMember(String username) {
        return memberDao.findByUsername(username).isPresent();
    }

    public Member findById(long id) {
        return memberDao.findById(id)
                .orElseThrow(() -> new DataAccessException(DataAccessErrorCode.MEMBER_NOT_FOUND));
    }
}
