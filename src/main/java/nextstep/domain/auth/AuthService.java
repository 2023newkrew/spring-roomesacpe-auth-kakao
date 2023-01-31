package nextstep.domain.auth;

import nextstep.infrastructure.web.JwtTokenProvider;
import nextstep.domain.member.Member;
import nextstep.domain.member.MemberDao;
import nextstep.interfaces.auth.dto.TokenRequest;
import nextstep.interfaces.auth.dto.TokenResponse;
import org.springframework.stereotype.Service;
import nextstep.interfaces.exception.AuthorizationException;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }


    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new AuthorizationException("아이디, 비밀번호가 알맞지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(member.getId().toString(), member.getRole().name());
        return new TokenResponse(accessToken);
    }

}
