package nextstep.service;

import lombok.RequiredArgsConstructor;
import nextstep.domain.domain.Member;
import nextstep.domain.model.request.MemberRequest;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.repository.MemberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    @Transactional
    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberDao.findById(id);
    }

}
