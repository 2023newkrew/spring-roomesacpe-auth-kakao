package nextstep.service;

import nextstep.support.util.JwtTokenProvider;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import nextstep.domain.Member;
import nextstep.dao.MemberDao;
import nextstep.support.exception.AuthorizationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest.getUsername(), tokenRequest.getPassword())) {
            throw new AuthorizationException();
        }

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private boolean checkInvalidLogin(String principal, String credentials) {
        Member member = memberDao.findByUsername(principal);
        return !member.getUsername().equals(principal) || member.checkWrongPassword(credentials);
    }
}
