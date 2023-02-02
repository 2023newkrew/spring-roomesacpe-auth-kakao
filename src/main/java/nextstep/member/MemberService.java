package nextstep.member;

import nextstep.support.NotExistEntityException;
import nextstep.support.PermissionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:secrets.yml")
public class MemberService {
    private final MemberDao memberDao;
    private final String secretKey;

    public MemberService(MemberDao memberDao, @Value("change-usertype-key") String secretKey) {
        this.memberDao = memberDao;
        this.secretKey = secretKey;
    }

    public Long create(MemberRequest memberRequest) {
        try {
            // 해당 username을 가진 유저가 이미 존재하는지 확인
            memberDao.findByUsername(memberRequest.getUsername());
            throw new IllegalArgumentException("이미 해당 이름을 가진 계정이 존재합니다.");
        } catch (EmptyResultDataAccessException e) {
            return memberDao.save(memberRequest.toEntity());
        }
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username);
        if (member == null) {
            throw new NotExistEntityException();
        }
        return member;
    }

    public Long editType(ChangeUserTypeRequest request) {
        // 멤버의 타입 변경을 위해서는 적절한 비밀키가 필요하다
        if (!request.getSecretKey().equals(secretKey)) {
            throw new PermissionException();
        }
        return memberDao.editType(request.getUserId(), request.getUserType());
    }
}
