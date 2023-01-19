package nextstep.publics.login;

import nextstep.framework.auth.jwt.JwtTokenProvider;
import nextstep.framework.exception.AuthErrorCode;
import nextstep.framework.exception.BusinessException;
import nextstep.publics.member.Member;
import nextstep.publics.member.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        Member member = memberDao.findByUsername(username);
        if(member.checkWrongPassword(password)) {
            throw new BusinessException(AuthErrorCode.INVALID_PASSWORD);
        }

        return new TokenResponse(jwtTokenProvider.createToken(Long.toString(member.getId()), member.getAuth()));
    }
}
