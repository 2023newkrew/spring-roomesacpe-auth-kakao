package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.support.Messages.*;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse issueToken(TokenRequest tokenRequest) {
        validateMember(tokenRequest);
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }

    private void validateMember(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        List<Member> member = memberDao.findByUsername(username);
        if (member.isEmpty()) {
            throw new NullPointerException(MEMBER_NOT_FOUND.getMessage());
        }
        if (passwordEncoder.matches(member.get(0).getPassword(), password)) {
            throw new AuthenticationServiceException(PASSWORD_INCORRECT.getMessage());
        }
    }
}
