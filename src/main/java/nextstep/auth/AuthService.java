package nextstep.auth;

import nextstep.auth.jwt.EncodedJwtToken;
import nextstep.auth.jwt.TokenRequest;
import nextstep.auth.jwt.TokenResponse;
import nextstep.exception.AuthErrorCode;
import nextstep.exception.BusinessException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final MemberDao memberDao;

    @Autowired
    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        Member member = memberDao.findByUsername(username);
        if (member.checkWrongPassword(password)) {
            throw new BusinessException(AuthErrorCode.INVALID_PASSWORD);
        }

        EncodedJwtToken encodedJwtToken = new EncodedJwtToken(member.getId()
                .toString());
        return new TokenResponse(encodedJwtToken.getRawToken());
    }
}
