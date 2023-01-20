package nextstep.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nextstep.member.Member;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public String login(Member member, TokenRequestDto tokenRequestDto) {
        if (member.checkAdmin()) {
            log.info("관리자 로그인");
            return jwtTokenProvider.createAdminToken(tokenRequestDto.getUsername());
        }
        log.info("사용자 로그인");
        return jwtTokenProvider.createToken(tokenRequestDto.getUsername());
    }


    public String findUsernameByToken(String token) {
        return jwtTokenProvider.getPrincipal(token);
    }
}
