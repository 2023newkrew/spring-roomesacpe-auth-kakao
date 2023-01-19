package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public String login(Member member, TokenRequestDto tokenRequestDto) {
        if (member.checkAdmin()) {
            return jwtTokenProvider.createAdminToken(tokenRequestDto.getUsername());
        }
        return jwtTokenProvider.createToken(tokenRequestDto.getUsername());
    }


    public String findUsernameByToken(String token) {
        return jwtTokenProvider.getPrincipal(token);
    }
}
