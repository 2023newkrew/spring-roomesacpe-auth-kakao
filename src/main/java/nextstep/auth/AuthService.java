package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberMapper;
import nextstep.support.exception.NoSuchMemberException;
import nextstep.support.exception.PasswordNotMatchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;
    private final MemberDao memberDao;

    @Transactional(readOnly = true)
    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());

        if(!member.getPassword().equals(tokenRequest.getPassword())){
            throw new PasswordNotMatchException();
        }

        return TokenResponse.of(jwtTokenProvider.createToken(memberMapper.memberToMemberDetails(member)));
    }
}
