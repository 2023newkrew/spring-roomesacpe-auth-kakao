package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.NoSuchMemberException;
import nextstep.support.exception.PasswordNotMatchException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if(ObjectUtils.isEmpty(member)) {
            throw new NoSuchMemberException();
        }

        if(!member.getPassword().equals(tokenRequest.getPassword())){
            throw new PasswordNotMatchException();
        }

        return TokenResponse.of(
                jwtTokenProvider.createToken(tokenRequest.getUsername())
        );
    }
}
