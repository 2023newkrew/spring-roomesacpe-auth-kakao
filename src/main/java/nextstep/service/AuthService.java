package nextstep.service;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.request.TokenRequest;
import nextstep.domain.model.response.TokenResponse;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.domain.domain.Member;
import nextstep.repository.MemberDao;
import nextstep.domain.mapper.MemberMapper;
import nextstep.infra.exception.auth.PasswordNotMatchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;
    private final MemberDao memberDao;

    @Transactional(readOnly = true)
    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsernameAndPassword(tokenRequest.getUsername(), tokenRequest.getPassword());

        return TokenResponse.of(jwtTokenProvider.createToken(memberMapper.memberToMemberDetails(member)));
    }
}
