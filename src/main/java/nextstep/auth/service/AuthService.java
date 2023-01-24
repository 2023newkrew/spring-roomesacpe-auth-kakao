package nextstep.auth.service;


import nextstep.auth.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(String username, String password);
}
