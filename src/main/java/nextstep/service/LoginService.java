package nextstep.service;

import lombok.RequiredArgsConstructor;
import nextstep.utils.JwtTokenProvider;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import nextstep.error.ApplicationException;
import nextstep.domain.member.Member;
import nextstep.domain.member.MemberDao;
import org.springframework.stereotype.Service;

import static nextstep.error.ErrorType.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = findByUsername(tokenRequest.getUsername());

        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new ApplicationException(MEMBER_NOT_FOUND);
        }

        return new TokenResponse(jwtTokenProvider.createToken(member.getId().toString()));
    }

    private Member findByUsername(String username) {
        try {
            return memberDao.findByUsername(username);
        } catch (RuntimeException e) {
            throw new ApplicationException(MEMBER_NOT_FOUND);
        }
    }

}
