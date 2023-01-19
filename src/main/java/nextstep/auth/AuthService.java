package nextstep.auth;

import nextstep.exception.BusinessException;
import nextstep.exception.ErrorCode;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
            throw new BusinessException(ErrorCode.WRONG_PASSWORD);
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    public boolean checkPassword(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        Member actualMember = memberDao.findByUsername(username);

        if (Objects.isNull(actualMember)) {
            return false;
        }
        return actualMember.getPassword().equals(password);
    }
}
