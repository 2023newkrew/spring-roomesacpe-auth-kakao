package nextstep.auth;

import static nextstep.common.exception.ExceptionMessage.NOT_EXIST_MEMBER;
import static nextstep.common.exception.ExceptionMessage.WRONG_PASSWORD;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.common.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRole;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberDao memberDao;

    public String login(TokenRequestDto tokenRequestDto) {
        return jwtTokenProvider.createToken(tokenRequestDto);
    }

    public String findUsernameByToken(final String token) {
        return jwtTokenProvider.getUsername(token);
    }

}
