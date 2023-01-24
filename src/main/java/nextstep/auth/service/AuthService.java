package nextstep.auth.service;

import nextstep.auth.config.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.exception.AuthorizationException;
import nextstep.member.domain.Member;
import nextstep.member.repository.MemberDao;
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
            throw new AuthorizationException("비밀번호를 잘못 입력했습니다.");
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
