package nextstep.auth.service;

import lombok.RequiredArgsConstructor;
import nextstep.auth.support.JwtTokenProvider;
import nextstep.auth.model.TokenRequest;
import nextstep.auth.model.TokenResponse;
import nextstep.member.model.Member;
import nextstep.member.dao.MemberDao;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest.getUsername(), tokenRequest.getPassword())) {
            throw new AuthorizationException();
        }

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private boolean checkInvalidLogin(String principal, String credentials) {
        Member member = memberDao.findByUsername(principal);
        if (member == null) {
            return true;
        }
        return member.checkWrongPassword(credentials);
    }
}
