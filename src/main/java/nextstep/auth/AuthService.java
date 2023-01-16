package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, JdbcTemplate jdbcTemplate) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {

        if (!checkPassword(tokenRequest)) {
            // TODO: throw exception
            return null;
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    public boolean checkPassword(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        Optional<Member> actualMember = memberDao.findByUsername(username);

        if (Objects.isNull(actualMember)) {
            return false;
        }
        if (actualMember.get().getPassword() != password) {
            return false;
        }
        return true;
    }
}
