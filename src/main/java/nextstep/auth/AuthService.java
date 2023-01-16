package nextstep.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public String login(TokenRequestDto tokenRequestDto) {
        return jwtTokenProvider.createToken(tokenRequestDto.getUsername());
    }
}
