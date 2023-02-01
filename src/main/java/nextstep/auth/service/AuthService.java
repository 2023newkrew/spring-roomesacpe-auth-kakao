package nextstep.auth.service;

import nextstep.auth.config.JwtTokenProvider;
import nextstep.auth.dto.TokenRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.exception.AuthorizationException;
import nextstep.exception.NotExistEntityException;
import nextstep.member.domain.Member;
import nextstep.member.repository.MemberDao;
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
            throw new AuthorizationException("비밀번호를 잘못 입력했습니다.");
        }
        Member member = memberDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(NotExistEntityException::new);
        if(member.checkWrongPassword(tokenRequest.getPassword())){
            throw new AuthorizationException();
        }

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername(), member.getRole());
        return new TokenResponse(accessToken);
    }

    public boolean checkPassword(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();

        Member actualMember = memberDao
                .findByUsername(username)
                .orElseThrow(
                        () -> new AuthorizationException("해당 username을 가진 맴버가 없습니다.")
                );

        return Objects.equals(actualMember.getPassword(), password);
    }
}